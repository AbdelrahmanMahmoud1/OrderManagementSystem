package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;

import java.util.List;

public class PurchaseDetailsResponse {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok

    private List<BuyingPurchase> purchaseList;
    private int purchaseCount;
    private double totalCost;

    public List<BuyingPurchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<BuyingPurchase> purchaseList) {
        this.purchaseList = purchaseList;
        this.purchaseCount = purchaseList.size();
        this.totalCost = 0;
        for (BuyingPurchase purchase : purchaseList)
            totalCost += purchase.getCost();
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
