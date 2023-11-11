package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;

public class BuyItemsResponse {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok

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
