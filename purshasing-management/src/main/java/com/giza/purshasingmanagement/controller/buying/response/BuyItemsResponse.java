package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.dto.buying.BuyingPurchaseDTO;

public class BuyItemsResponse {
    private BuyingPurchaseDTO purchase;
    private String inventoryMessage;

    public BuyingPurchaseDTO getPurchase() {
        return purchase;
    }

    public void setPurchase(BuyingPurchaseDTO purchase) {
        this.purchase = purchase;
    }

    public String getInventoryMessage() {
        return inventoryMessage;
    }

    public void setInventoryMessage(String inventoryMessage) {
        this.inventoryMessage = inventoryMessage;
    }
}
