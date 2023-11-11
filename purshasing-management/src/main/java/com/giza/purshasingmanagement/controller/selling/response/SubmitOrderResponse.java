package com.giza.purshasingmanagement.controller.selling.response;

import java.util.Map;

public class SubmitOrderResponse {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok

    private Map<String, Double> productRevenuePair;
    private long purchaseId;
    private String message;

    public Map<String, Double> getProductRevenuePair() {
        return productRevenuePair;
    }

    public void setProductRevenuePair(Map<String, Double> productRevenuePair) {
        this.productRevenuePair = productRevenuePair;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }
}
