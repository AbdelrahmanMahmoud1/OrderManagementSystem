package com.giza.purshasingmanagement.controller.response;

import com.giza.purshasingmanagement.entity.Purchase;

import java.util.List;

public class PurchaseDetailsResponse {

    private List<Purchase> purchaseList;
    private int purchaseCount;
    private double totalRevenue;

    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
        this.purchaseCount = purchaseList.size();
        this.totalRevenue = 0;
        for (Purchase purchase : purchaseList)
            totalRevenue += purchase.getRevenue();
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}
