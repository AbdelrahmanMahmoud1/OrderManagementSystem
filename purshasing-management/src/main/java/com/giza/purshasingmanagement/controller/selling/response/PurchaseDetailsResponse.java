package com.giza.purshasingmanagement.controller.selling.response;

import com.giza.purshasingmanagement.entity.selling.SellingPurchase;

import java.util.List;

public class PurchaseDetailsResponse {

    private List<SellingPurchase> purchaseList;
    private int purchaseCount;
    private double totalRevenue;

    public List<SellingPurchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<SellingPurchase> purchaseList) {
        this.purchaseList = purchaseList;
        this.purchaseCount = purchaseList.size();
        this.totalRevenue = 0;
        for (SellingPurchase purchase : purchaseList)
            totalRevenue += purchase.getRevenue();
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}
