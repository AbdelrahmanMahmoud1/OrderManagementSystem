package com.giza.purshasingmanagement.controller;

import com.giza.purshasingmanagement.controller.response.RevenueSummaryResponse;
import com.giza.purshasingmanagement.controller.response.IncreasePurchasingResponse;
import com.giza.purshasingmanagement.entity.Order;
import com.giza.purshasingmanagement.entity.ProductRevenue;
import com.giza.purshasingmanagement.kafka.KafkaProducer;
import com.giza.purshasingmanagement.service.RevenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PurchasingController {

    private final Logger logger = LoggerFactory.getLogger(PurchasingController.class);

    private final RevenueService revenueService;
    private final KafkaProducer<Map<Long, Double>> kafkaProducer;

    @Autowired
    public PurchasingController(
            RevenueService revenueService,
            KafkaProducer<Map<Long, Double>> kafkaProducer) {
        this.revenueService = revenueService;
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/submit-order")
    public ResponseEntity<IncreasePurchasingResponse> submitOrder(@RequestBody Order order) {
        checkOrderValidity(order);
        logger.info("Received: " + order);
        Map<Long, Double> pRevenuePairs = calculateProductRevenuePairs(order);
        kafkaProducer.sendMessage(pRevenuePairs);
        IncreasePurchasingResponse response = new IncreasePurchasingResponse();
        response.setProductRevenuePair(pRevenuePairs);
        response.setMessage("Successful Order Purchase");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    private Map<Long, Double> calculateProductRevenuePairs(Order order) {
        Map<Long, Double> pRevenuePairs = new HashMap<>();
        // Set initial value for each productId
        order.getProducts().forEach(product -> pRevenuePairs.put((long) product.getId(), 0.0));
        // Add to the present value the revenue
        order.getProducts().forEach(product -> {
            double revenue = revenueService.save(product);
            pRevenuePairs.put((long) product.getId(), pRevenuePairs.get((long) product.getId()) + revenue);
            logger.info("Increased " + product.getQuantity() + " to product with id " + product.getId());
        });
        return pRevenuePairs;
    }

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

    @GetMapping("/get-revenue-summary")
    public ResponseEntity<RevenueSummaryResponse> getPurchasing() {
        logger.info("Getting revenue summary");
        RevenueSummaryResponse response = new RevenueSummaryResponse();
        List<ProductRevenue> revenues = revenueService.findAll();
        response.setProductsRevenues(revenues);
        logger.info("Found " + response.getProductsPurchasedCount()
                + " products purchased, with a total revenue of " + response.getTotalRevenue());
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}
