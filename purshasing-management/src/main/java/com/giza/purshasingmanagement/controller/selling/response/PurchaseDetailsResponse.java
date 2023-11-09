package com.giza.purshasingmanagement.controller.selling.response;

import com.giza.purshasingmanagement.dto.selling.SellingPurchaseDTO;

import java.util.List;

public class PurchaseDetailsResponse {

    private List<SellingPurchaseDTO> purchaseList;
    private int purchaseCount;
    private double totalRevenue;

    public List<SellingPurchaseDTO> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<SellingPurchaseDTO> purchaseList) {
        this.purchaseList = purchaseList;
        this.purchaseCount = purchaseList.size();
        this.totalRevenue = 0;
        for (SellingPurchaseDTO purchase : purchaseList)
            totalRevenue += purchase.getRevenue();
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}
