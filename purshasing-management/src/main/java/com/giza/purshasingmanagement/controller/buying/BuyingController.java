package com.giza.purshasingmanagement.controller.buying;

import com.giza.purshasingmanagement.controller.buying.response.BuyItemsResponse;
import com.giza.purshasingmanagement.controller.buying.response.CostSummaryResponse;
import com.giza.purshasingmanagement.controller.buying.response.InventoryStockResponse;
import com.giza.purshasingmanagement.controller.buying.response.PurchaseDetailsResponse;
import com.giza.purshasingmanagement.entity.Order;
import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;
import com.giza.purshasingmanagement.entity.buying.ProductCost;
import com.giza.purshasingmanagement.entity.db.ProductDB;
import com.giza.purshasingmanagement.service.buying.BuyingService;
import com.giza.purshasingmanagement.service.buying.CostService;
import org.antlr.v4.runtime.misc.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.giza.purshasingmanagement.AppConstants.INVENTORY_BASE_URL;

@RestController
@RequestMapping("/buying")
public class BuyingController {

    private final Logger logger = LoggerFactory.getLogger(BuyingController.class);

    private final CostService costService;
    private final BuyingService buyingService;

    @Autowired
    public BuyingController(
            CostService costService,
            BuyingService buyingService) {
        this.costService = costService;
        this.buyingService = buyingService;
    }

    @PostMapping("/submit-order")
    public ResponseEntity<BuyItemsResponse> submitOrder(@RequestBody Order order) {
        checkOrderValidity(order);
        ResponseEntity<InventoryStockResponse> inventoryResponse = new RestTemplate().postForEntity(
                INVENTORY_BASE_URL + "products/purchased",
                order.getProducts(),
                InventoryStockResponse.class);

        BuyItemsResponse response = new BuyItemsResponse();

        if (inventoryResponse.getStatusCode() == HttpStatus.OK) {
            List<Product> products;
            if (inventoryResponse.getBody() != null && inventoryResponse.getBody().getProducts() != null)
                products = inventoryResponse.getBody().getProducts();
            else
                products = new ArrayList<>();

            BuyingPurchase purchase = createPurchaseRecord(products);
            calculateProductCostPairs(purchase);

            response.setPurchase(purchase);
            response.setInventoryMessage("Successfully added to Inventory");
            long purchaseId = buyingService.save(purchase);
            purchase.setPurchaseId(purchaseId);

            return ResponseEntity.accepted().body(response);
        }
        if (inventoryResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
            response.setInventoryMessage("Bad Request to Inventory");
            return ResponseEntity.badRequest().body(response);
        }
        response.setInventoryMessage("Internal Server Error from Inventory");
        return ResponseEntity.internalServerError().body(response);
    }

    /** Creating purchase record by processing the incoming inventory products added **/
    private BuyingPurchase createPurchaseRecord(List<Product> inventoryProducts) {
        HashMap<String, Pair<Integer, Float>> uniqueProductMap = new HashMap<>();
        inventoryProducts.forEach(p -> uniqueProductMap.put(p.getName(), new Pair<>(0, 0.0f)));
        inventoryProducts.forEach(p -> {
            Pair<Integer, Float> quantityPrice = uniqueProductMap.get(p.getName());
            uniqueProductMap.put(
                    p.getName(),
                    new Pair<>(p.getQuantity() + quantityPrice.a, p.getPrice() + quantityPrice.b));
        });
        BuyingPurchase purchase = new BuyingPurchase();
        purchase.setPurchaseDate(new Date(System.currentTimeMillis()));
        List<ProductDB> products = new ArrayList<>();
        double cost = 0;
        for (Map.Entry<String, Pair<Integer, Float>> entry : uniqueProductMap.entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue().a;
            float price = entry.getValue().b;
            products.add(new ProductDB(name, quantity, price));
            cost += quantity * price;
        }
        purchase.setProducts(products);
        purchase.setCost(cost);
        long purchaseId = buyingService.save(purchase);
        purchase.setPurchaseId(purchaseId);
        return purchase;
    }

    /** Creating purchase record by processing the order id, date and products **/
    private Map<String, Double> calculateProductCostPairs(BuyingPurchase purchase) {
        Map<String, Double> pCostPairs = new HashMap<>();
        purchase.getProducts().forEach(product -> {
            double revenue = costService.save(product);
            pCostPairs.put(product.getName(), revenue);
            logger.info("Bought " + product.getQuantity() + " of product with id " + product.getName());
        });
        return pCostPairs;
    }

    /** Checking order validity through products **/
    private void checkOrderValidity(Order order) {
        if (order == null || order.getProducts() == null) {
            logger.error("Order with no products");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order with no products");
        }
        order.getProducts().forEach(product -> {
            if (product.getQuantity() <= 0 || product.getPrice() <= 0) {
                logger.error("Invalid price/quantity");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid price/quantity");
            }
            else if (product.getName().isBlank()){
                logger.error("Invalid product name");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product name");
            }
        });
    }

    @GetMapping("/get-purchase-details")
    public ResponseEntity<PurchaseDetailsResponse> getPurchaseDetails() {
        logger.info("Buying: Getting purchase details");
        List<BuyingPurchase> purchaseList = buyingService.findAll();
        PurchaseDetailsResponse response = new PurchaseDetailsResponse();
        response.setPurchaseList(purchaseList);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/get-cost-summary")
    public ResponseEntity<CostSummaryResponse> getCostSummary() {
        logger.info("Getting cost summary");
        CostSummaryResponse response = new CostSummaryResponse();
        List<ProductCost> costs = costService.findAll();
        response.setProductsCosts(costs);
        logger.info("Found " + response.getProductsPurchasedCount()
                + " product(s) purchased, with a total cost of " + response.getTotalCost());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}
