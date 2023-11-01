package com.giza.purshasingmanagement.controller.response;

import com.giza.purshasingmanagement.entity.Purchase;

import java.util.List;

public class GetPurchasingResponse {

    private List<Purchase> purchases;
    private int productsPurchased;

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
        this.productsPurchased = purchases.size();
    }

    public int getProductsPurchased() {
        return productsPurchased;
    }
}
