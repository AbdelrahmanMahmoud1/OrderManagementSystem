package com.giza.purshasingmanagement.controller.selling.response;

import java.util.Map;

public class SubmitOrderResponse {

    private Map<String, Double> productRevenuePair;
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
}
