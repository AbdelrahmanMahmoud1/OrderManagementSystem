package com.giza.purshasingmanagement.controller.selling;

import com.giza.purshasingmanagement.Utils;
import com.giza.purshasingmanagement.controller.selling.response.PurchaseDetailsResponse;
import com.giza.purshasingmanagement.controller.selling.response.RevenueSummaryResponse;
import com.giza.purshasingmanagement.controller.selling.response.SellItemsResponse;
import com.giza.purshasingmanagement.dto.buying.OrderDTO;
import com.giza.purshasingmanagement.service.selling.SellingService;
import com.giza.purshasingmanagement.service.selling.RevenueService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/selling")
@Getter
@Setter
@RequiredArgsConstructor
public class SellingController {

    private final Logger logger = LoggerFactory.getLogger(SellingController.class);

    private final RevenueService revenueService;
    private final SellingService sellingService;

    @PostMapping("/submit-order")
    public ResponseEntity<SellItemsResponse> submitOrder(@RequestBody OrderDTO order) {
        logger.info("Received: " + order);
        Utils.checkOrderValidity(order);
        SellItemsResponse response = sellingService.checkAndSubmitOrder(order);
        if (response.getPurchase() != null)
            revenueService.submitPurchase(response.getPurchase());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/get-purchase-details")
    public ResponseEntity<PurchaseDetailsResponse> getPurchaseDetails() {
        logger.info("Selling: Getting purchase details");
        PurchaseDetailsResponse response = sellingService.getPurchaseDetails();
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/get-revenue-summary")
    public ResponseEntity<RevenueSummaryResponse> getRevenueSummary() {
        logger.info("Getting revenue summary");
        RevenueSummaryResponse response = revenueService.getRevenueSummary();
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}
