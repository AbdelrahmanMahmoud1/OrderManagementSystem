package com.giza.purshasingmanagement.controller;

import com.giza.purshasingmanagement.controller.response.GetPurchasingResponse;
import com.giza.purshasingmanagement.controller.response.IncreasePurchasingResponse;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PurchasingController {

    private final Logger logger = LoggerFactory.getLogger(PurchasingController.class);

    private final PurchaseService purchaseService;
    private final KafkaProducer<Purchase> kafkaProducer;

    @Autowired
    public PurchasingController(
            PurchaseService purchaseService,
            KafkaProducer<Purchase> kafkaProducer) {
        this.purchaseService = purchaseService;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/sell-product")
    public ResponseEntity<String> sellProduct() {
        logger.info("Selling product");
        return new ResponseEntity<>("Product Sold", HttpStatus.OK);
    }

    @PostMapping("/increase-purchasing")
    public ResponseEntity<IncreasePurchasingResponse> increasePurchasing(@RequestBody Purchase purchase) {
        logger.info("Increasing purchase");
        if (purchase == null) {
            logger.error("No purchase found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        logger.info(purchase.toString());
        purchase.setPurchaseTime(LocalDateTime.now());
        long id = purchaseService.save(purchase);
        purchase.setId(id);
        IncreasePurchasingResponse response = new IncreasePurchasingResponse();
        response.setPurchase(purchase);
        kafkaProducer.sendMessage(purchase);
        response.setMessage("Successful Purchase");
        logger.info("Increased purchase with id " + id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-purchasing")
    public ResponseEntity<GetPurchasingResponse> getPurchasing() {
        logger.info("Getting all purchases ");
        GetPurchasingResponse response = new GetPurchasingResponse();
        List<Purchase> allPurchases = purchaseService.findAll();
        response.setPurchases(allPurchases);
        logger.info("Found " + response.getPurchaseCount() + " purchases");
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
