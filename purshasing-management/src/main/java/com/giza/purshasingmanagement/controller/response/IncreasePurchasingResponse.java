package com.giza.purshasingmanagement.controller.response;

import java.util.Map;

public class IncreasePurchasingResponse {

    private Map<Long, Double> productRevenuePair;
    private String message;

    public Map<Long, Double> getProductRevenuePair() {
        return productRevenuePair;
    }

    public void setProductRevenuePair(Map<Long, Double> productRevenuePair) {
        this.productRevenuePair = productRevenuePair;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
