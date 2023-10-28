package com.giza.purshasingmanagement.controller.response;

import com.giza.purshasingmanagement.entity.Purchase;

import java.util.List;

public class GetPurchasingResponse {

    private List<Purchase> purchases;
    private int purchaseCount;

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
        this.purchaseCount = purchases.size();
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }
}
