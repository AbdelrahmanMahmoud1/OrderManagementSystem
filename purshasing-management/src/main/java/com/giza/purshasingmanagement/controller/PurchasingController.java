package com.giza.purshasingmanagement.controller;

import com.giza.purshasingmanagement.controller.response.GetPurchasingResponse;
import com.giza.purshasingmanagement.controller.response.IncreasePurchasingResponse;
import com.giza.purshasingmanagement.entity.Order;
import com.giza.purshasingmanagement.entity.Purchase;
import com.giza.purshasingmanagement.kafka.KafkaProducer;
import com.giza.purshasingmanagement.service.PurchaseService;
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

    private final PurchaseService purchaseService;
    private final KafkaProducer<Map<Long, Double>> kafkaProducer;

    @Autowired
    public PurchasingController(
            PurchaseService purchaseService,
            KafkaProducer<Map<Long, Double>> kafkaProducer) {
        this.purchaseService = purchaseService;
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/increase-purchasing")
    public ResponseEntity<IncreasePurchasingResponse> increasePurchasing(@RequestBody Order order) {
        checkOrderValidity(order);
        logger.info("Received: " + order);
        Map<Long, Double> pairs = new HashMap<>();
        order.getProducts().forEach(product -> pairs.put((long) product.getId(), 0.0));
        order.getProducts().forEach(product -> {
            double revenue = purchaseService.save(product);
            pairs.put((long) product.getId(), pairs.get((long) product.getId()) + revenue);
            logger.info("Increased " + product.getQuantity() + " to product with id " + product.getId());
        });
        IncreasePurchasingResponse response = new IncreasePurchasingResponse();
        response.setProductRevenuePair(pairs);
        kafkaProducer.sendMessage(pairs);
        response.setMessage("Successful Purchase");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
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

    @GetMapping("/get-purchasing")
    public ResponseEntity<GetPurchasingResponse> getPurchasing() {
        logger.info("Getting all purchases");
        GetPurchasingResponse response = new GetPurchasingResponse();
        List<Purchase> allPurchases = purchaseService.findAll();
        response.setPurchases(allPurchases);
        logger.info("Found " + response.getProductsPurchased() + " purchases");
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @DeleteMapping("/delete-purchasing")
    public ResponseEntity<String> deletePurchasing(@RequestParam("purchaseId") long purchaseId) {
        logger.info("Trying to delete purchase with id " + purchaseId);
        Purchase purchase = purchaseService.findById(purchaseId);
        if (purchase == null) {
            logger.error("Cannot find purchase with id " + purchaseId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        purchaseService.deleteById(purchaseId);
        logger.info("Deleted purchase with id " + purchaseId);
        return new ResponseEntity<>("Deleted purchase with id " + purchaseId, HttpStatus.CREATED);
    }
}
