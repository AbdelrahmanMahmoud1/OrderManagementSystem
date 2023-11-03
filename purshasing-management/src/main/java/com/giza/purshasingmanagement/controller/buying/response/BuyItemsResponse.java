package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;

public class BuyItemsResponse {
    private BuyingPurchase purchase;
    private String inventoryMessage;

    public BuyingPurchase getPurchase() {
        return purchase;
    }

    public void setPurchase(BuyingPurchase purchase) {
        this.purchase = purchase;
    }

    public String getInventoryMessage() {
        return inventoryMessage;
    }

    public void setInventoryMessage(String inventoryMessage) {
        this.inventoryMessage = inventoryMessage;
    }
}
