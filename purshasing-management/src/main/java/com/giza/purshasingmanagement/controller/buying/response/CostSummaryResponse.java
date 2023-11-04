package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.entity.buying.ProductCost;

import java.util.List;

public class CostSummaryResponse {

    private List<ProductCost> productsCosts;
    private int productsPurchasedCount;
    private double totalCost;

    public List<ProductCost> getProductsCosts() {
        return productsCosts;
    }

    public void setProductsCosts(List<ProductCost> productsCosts) {
        this.productsCosts = productsCosts;
        this.productsPurchasedCount = productsCosts.size();
        this.totalCost = 0;
        productsCosts.forEach(pRevenue -> totalCost += pRevenue.getRevenue());
    }

    public int getProductsPurchasedCount() {
        return productsPurchasedCount;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
