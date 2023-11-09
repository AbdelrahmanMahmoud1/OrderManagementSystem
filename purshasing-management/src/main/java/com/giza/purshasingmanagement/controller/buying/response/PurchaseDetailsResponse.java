package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.dto.buying.BuyingPurchaseDTO;

import java.util.List;

public class PurchaseDetailsResponse {

    private List<BuyingPurchaseDTO> purchaseList;
    private int purchaseCount;
    private double totalCost;

    public List<BuyingPurchaseDTO> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<BuyingPurchaseDTO> purchaseList) {
        this.purchaseList = purchaseList;
        this.purchaseCount = purchaseList.size();
        this.totalCost = 0;
        for (BuyingPurchaseDTO purchase : purchaseList)
            totalCost += purchase.getCost();
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
