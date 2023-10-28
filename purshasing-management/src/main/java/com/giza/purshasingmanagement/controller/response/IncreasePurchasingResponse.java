package com.giza.purshasingmanagement.controller.response;

import com.giza.purshasingmanagement.entity.Purchase;

import java.util.List;

public class IncreasePurchasingResponse {

    private Purchase purchase;
    private String message;

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
