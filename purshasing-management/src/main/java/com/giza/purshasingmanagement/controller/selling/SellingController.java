package com.giza.purshasingmanagement.controller.selling;

import com.giza.purshasingmanagement.controller.selling.response.PurchaseDetailsResponse;
import com.giza.purshasingmanagement.controller.selling.response.RevenueSummaryResponse;
import com.giza.purshasingmanagement.controller.selling.response.SubmitOrderResponse;
import com.giza.purshasingmanagement.entity.Order;
import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.db.ProductDB;
import com.giza.purshasingmanagement.entity.selling.ProductRevenue;
import com.giza.purshasingmanagement.entity.selling.SellingPurchase;
import com.giza.purshasingmanagement.kafka.KafkaProducer;
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

    private final Logger logger = LoggerFactory.getLogger(SellingController.class);

    private final RevenueService revenueService;
    private final SellingService sellingService;
    private final KafkaProducer<Map<Long, Double>> kafkaProducer;

    @Autowired
    public SellingController(
            RevenueService revenueService,
            SellingService sellingService,
            KafkaProducer<Map<Long, Double>> kafkaProducer) {
        this.revenueService = revenueService;
        this.sellingService = sellingService;
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/submit-order")
    public ResponseEntity<SubmitOrderResponse> submitOrder(@RequestBody Order order) {
        checkOrderValidity(order);
        logger.info("Received: " + order);
        SellingPurchase purchase = createPurchaseRecord(order);
        Map<Long, Double> pRevenuePairs = calculateProductRevenuePairs(purchase);
        kafkaProducer.sendMessage(pRevenuePairs);
        SubmitOrderResponse response = new SubmitOrderResponse();
        response.setPurchaseId(purchase.getPurchaseId());
        response.setProductRevenuePair(pRevenuePairs);
        response.setMessage("Successful Order Purchase");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    /** Creating purchase record by processing the order id, date and products **/
    private SellingPurchase createPurchaseRecord(Order order) {
        HashMap<Integer, Pair<Integer, Float>> uniqueProductMap = new HashMap<>();
        order.getProducts().forEach(p -> uniqueProductMap.put(p.getId(), new Pair<>(0, 0.0f)));
        order.getProducts().forEach(p -> {
            Pair<Integer, Float> quantityPrice = uniqueProductMap.get(p.getId());
            uniqueProductMap.put(
                    p.getId(),
                    new Pair<>(p.getQuantity() + quantityPrice.a, p.getPrice() + quantityPrice.b));
        });
        SellingPurchase purchase = new SellingPurchase();
        purchase.setOrderId(order.getId());
        purchase.setPurchaseDate(new Date(System.currentTimeMillis()));
        List<ProductDB> products = new ArrayList<>();
        double revenue = 0;
        for (Map.Entry<Integer, Pair<Integer, Float>> entry : uniqueProductMap.entrySet()) {
            int id = entry.getKey();
            int quantity = entry.getValue().a;
            float price = entry.getValue().b;
            products.add(new ProductDB(id, quantity, price));
            revenue += quantity * price;
        }
        purchase.setProducts(products);
        purchase.setRevenue(revenue);
        long purchaseId = sellingService.save(purchase);
        purchase.setPurchaseId(purchaseId);
        return purchase;
    }

    /** Creating purchase record by processing the order id, date and products **/
    private Map<Long, Double> calculateProductRevenuePairs(SellingPurchase purchase) {
        Map<Long, Double> pRevenuePairs = new HashMap<>();
        purchase.getProducts().forEach(product -> {
            double revenue = revenueService.save(product);
            pRevenuePairs.put((long) product.getId(), revenue);
            logger.info("Increased " + product.getQuantity() + " to product with id " + product.getId());
        });
        return pRevenuePairs;
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
        });
    }

    @GetMapping("/get-purchase-details")
    public ResponseEntity<PurchaseDetailsResponse> getPurchaseDetails() {
        logger.info("Selling: Getting purchase details");
        List<SellingPurchase> purchaseList = sellingService.findAll();
        PurchaseDetailsResponse response = new PurchaseDetailsResponse();
        response.setPurchaseList(purchaseList);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/get-revenue-summary")
    public ResponseEntity<RevenueSummaryResponse> getRevenueSummary() {
        logger.info("Getting revenue summary");
        RevenueSummaryResponse response = new RevenueSummaryResponse();
        List<ProductRevenue> revenues = revenueService.findAll();
        response.setProductsRevenues(revenues);
        logger.info("Found " + response.getProductsPurchasedCount()
                + " product(s) purchased, with a total revenue of " + response.getTotalRevenue());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}
