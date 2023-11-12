package com.giza.purshasingmanagement.controller.buying;

import com.giza.purshasingmanagement.controller.buying.response.BuyItemsResponse;
import com.giza.purshasingmanagement.controller.buying.response.CostSummaryResponse;
import com.giza.purshasingmanagement.controller.buying.response.InventoryStockResponse;
import com.giza.purshasingmanagement.controller.buying.response.PurchaseDetailsResponse;
import com.giza.purshasingmanagement.dto.buying.BuyingPurchaseDTO;
import com.giza.purshasingmanagement.dto.buying.CostDTO;
import com.giza.purshasingmanagement.dto.buying.OrderDTO;
import com.giza.purshasingmanagement.dto.buying.BuyingProductDTO;
import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;
import com.giza.purshasingmanagement.entity.buying.ProductCost;
import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.service.buying.BuyingService;
import com.giza.purshasingmanagement.service.buying.CostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Getter
@Setter
@RequiredArgsConstructor
public class BuyingController {
    // TODO: 11/11/2023 nice to have use @controllerAdvice for exception and success handelling https://www.baeldung.com/exception-handling-for-rest-with-spring https://www.baeldung.com/spring-security-exceptionhandler
    private final Logger logger = LoggerFactory.getLogger(BuyingController.class);

    private final CostService costService;
    private final BuyingService buyingService;

    @PostMapping("/submit-order")
    public ResponseEntity<BuyItemsResponse> submitOrder(@RequestBody OrderDTO order) {
        // TODO: 11/11/2023 move business logic to service class controller only responsibility to call service
        checkOrderValidity(order);
        ResponseEntity<InventoryStockResponse> inventoryResponse = new RestTemplate().postForEntity(
                INVENTORY_BASE_URL + "products/purchased",
                order.getProducts(),
                InventoryStockResponse.class);

        BuyItemsResponse response = new BuyItemsResponse();

        if (inventoryResponse.getStatusCode() == HttpStatus.OK) {
            List<BuyingProductDTO> products;
            if (inventoryResponse.getBody() != null && inventoryResponse.getBody().getProducts() != null)
                products = inventoryResponse.getBody().getProducts();
            else
                products = new ArrayList<>();

            // TODO: 11/11/2023 move logic to be inside if no need to send an empty list to do nothing
            BuyingPurchase purchase = createPurchaseRecord(products);
            calculateProductCostPairs(purchase);

            response.setPurchase(BuyingPurchaseDTO.entityToDTO(purchase));
            response.setInventoryMessage("Successfully added to Inventory");
            long purchaseId = buyingService.save(purchase);

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
    private BuyingPurchase createPurchaseRecord(List<BuyingProductDTO> inventoryProducts) {
        // TODO: 11/11/2023 need to schedule call to discuss what is going on here as it is over-engineering to needed task for it
        HashMap<String, Pair<Integer, Float>> uniqueProductMap = new HashMap<>();
        // TODO: 11/11/2023 instead of foreach use stream
        inventoryProducts.forEach(p -> uniqueProductMap.put(p.getName(), new Pair<>(0, 0.0f)));
        inventoryProducts.forEach(p -> {
            Pair<Integer, Float> quantityPrice = uniqueProductMap.get(p.getName());
            uniqueProductMap.put(
                    p.getName(),
                    new Pair<>(p.getQuantity() + quantityPrice.a, p.getPrice() + quantityPrice.b));
        });
        BuyingPurchase purchase = new BuyingPurchase();
        purchase.setPurchaseDate(new Date(System.currentTimeMillis()));
        List<Product> products = new ArrayList<>();
        double cost = 0;
        for (Map.Entry<String, Pair<Integer, Float>> entry : uniqueProductMap.entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue().a;
            float price = entry.getValue().b;
            products.add(new Product(name, quantity, price));
            cost += quantity * price;
        }
        purchase.setProducts(products);
        purchase.setCost(cost);
        // TODO: 11/11/2023 why return id here and set it in the actual entity when .save in jpa returns the whole entity? thats redundant
        long purchaseId = buyingService.save(purchase);
        purchase.setPurchaseId(purchaseId);
        return purchase;
    }

    /** Creating purchase record by processing the order id, date and products **/
    // TODO: 11/11/2023 why have a return value when its not used?
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
    // TODO: 11/11/2023 duplicate code extract this and the buyingcontroller one into a utility class to avoid duplication
    private void checkOrderValidity(OrderDTO order) {
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
        // TODO: 11/11/2023 remove logic to business service class not in controller
        logger.info("Buying: Getting purchase details");
        List<BuyingPurchase> purchaseList = buyingService.findAll();
        PurchaseDetailsResponse response = new PurchaseDetailsResponse();
        List<BuyingPurchaseDTO> purchaseDTOs = new ArrayList<>();
        purchaseList.forEach(p -> purchaseDTOs.add(BuyingPurchaseDTO.entityToDTO(p)));
        response.setPurchaseList(purchaseDTOs);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/get-cost-summary")
    public ResponseEntity<CostSummaryResponse> getCostSummary() {
        // TODO: 11/11/2023 remove logic to business service class not in controller
        logger.info("Getting cost summary");
        CostSummaryResponse response = new CostSummaryResponse();
        List<ProductCost> costs = costService.findAll();
        List<CostDTO> costDTOs = new ArrayList<>();
        costs.forEach(c -> costDTOs.add(CostDTO.entityToDTO(c)));
        response.setProductsCosts(costDTOs);
        logger.info("Found " + response.getProductsPurchasedCount()
                + " product(s) purchased, with a total cost of " + response.getTotalCost());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}
