package com.giza.purshasingmanagement.controller.selling;

import com.giza.purshasingmanagement.controller.selling.response.PurchaseDetailsResponse;
import com.giza.purshasingmanagement.controller.selling.response.RevenueSummaryResponse;
import com.giza.purshasingmanagement.controller.selling.response.SubmitOrderResponse;
import com.giza.purshasingmanagement.dto.buying.OrderDTO;
import com.giza.purshasingmanagement.dto.selling.RevenueDTO;
import com.giza.purshasingmanagement.dto.selling.SellingPurchaseDTO;
import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.selling.ProductRevenue;
import com.giza.purshasingmanagement.entity.selling.SellingPurchase;
import com.giza.purshasingmanagement.service.selling.SellingService;
import com.giza.purshasingmanagement.service.selling.RevenueService;
import org.antlr.v4.runtime.misc.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/selling")
public class SellingController {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok
    // TODO: 11/11/2023 for constructor autowiring you can do this @RequiredArgsConstructor(onConstructor = @__(@Autowired)) but not needed

    private final Logger logger = LoggerFactory.getLogger(SellingController.class);

    private final RevenueService revenueService;
    private final SellingService sellingService;

    @Autowired
    public SellingController(RevenueService revenueService, SellingService sellingService) {
        this.revenueService = revenueService;
        this.sellingService = sellingService;
    }

    @PostMapping("/submit-order")
    public ResponseEntity<SubmitOrderResponse> submitOrder(@RequestBody OrderDTO order) {
        // TODO: 11/11/2023 move business logic to service class
        checkOrderValidity(order);
        logger.info("Received: " + order);
        SellingPurchase purchase = createPurchaseRecord(order);
        Map<String, Double> pRevenuePairs = calculateProductRevenuePairs(purchase);
        SubmitOrderResponse response = new SubmitOrderResponse();
        response.setProductRevenuePair(pRevenuePairs);
        response.setMessage("Successful Order Purchase");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    /** Creating purchase record by processing the order id, date and products **/
    private SellingPurchase createPurchaseRecord(OrderDTO order) {
        HashMap<String, Pair<Integer, Float>> uniqueProductMap = new HashMap<>();
        order.getProducts().forEach(p -> uniqueProductMap.put(p.getName(), new Pair<>(0, 0.0f)));
        order.getProducts().forEach(p -> {
            Pair<Integer, Float> quantityPrice = uniqueProductMap.get(p.getName());
            uniqueProductMap.put(
                    p.getName(),
                    new Pair<>(p.getQuantity() + quantityPrice.a, p.getPrice() + quantityPrice.b));
        });
        SellingPurchase purchase = new SellingPurchase();
        purchase.setOrderId(order.getId());
        purchase.setPurchaseDate(new Date(System.currentTimeMillis()));
        List<Product> products = new ArrayList<>();
        double revenue = 0;
        for (Map.Entry<String, Pair<Integer, Float>> entry : uniqueProductMap.entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue().a;
            float price = entry.getValue().b;
            products.add(new Product(name, quantity, price));
            revenue += quantity * price;
        }
        purchase.setProducts(products);
        purchase.setRevenue(revenue);
        long purchaseId = sellingService.save(purchase);
        purchase.setPurchaseId(purchaseId);
        return purchase;
    }

    /** Creating purchase record by processing the order id, date and products **/
    private Map<String, Double> calculateProductRevenuePairs(SellingPurchase purchase) {
        Map<String, Double> pRevenuePairs = new HashMap<>();
        purchase.getProducts().forEach(product -> {
            double revenue = revenueService.save(product);
            pRevenuePairs.put(product.getName(), revenue);
            logger.info("Increased " + product.getQuantity() + " to product: " + product.getName());
        });
        return pRevenuePairs;
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
        });
    }

    @GetMapping("/get-purchase-details")
    public ResponseEntity<PurchaseDetailsResponse> getPurchaseDetails() {
        logger.info("Selling: Getting purchase details");
        List<SellingPurchase> purchaseList = sellingService.findAll();
        List<SellingPurchaseDTO> purchaseListDTO = new ArrayList<>();
        purchaseList.forEach(p -> purchaseListDTO.add(SellingPurchaseDTO.entityToDTO(p)));
        PurchaseDetailsResponse response = new PurchaseDetailsResponse();
        response.setPurchaseList(purchaseListDTO);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/get-revenue-summary")
    public ResponseEntity<RevenueSummaryResponse> getRevenueSummary() {
        logger.info("Getting revenue summary");
        RevenueSummaryResponse response = new RevenueSummaryResponse();
        List<ProductRevenue> revenues = revenueService.findAll();
        List<RevenueDTO> revenueDTOs = new ArrayList<>();
        revenues.forEach(r -> revenueDTOs.add(RevenueDTO.entityToDTO(r)));
        response.setProductsRevenues(revenueDTOs);
        logger.info("Found " + response.getProductsPurchasedCount()
                + " product(s) purchased, with a total revenue of " + response.getTotalRevenue());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}
