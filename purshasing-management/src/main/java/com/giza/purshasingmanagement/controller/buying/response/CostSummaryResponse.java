package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.dto.buying.CostDTO;

import java.util.List;

public class CostSummaryResponse {

    private List<CostDTO> productsCosts;
    private int productsPurchasedCount;
    private double totalCost;

    public List<CostDTO> getProductsCosts() {
        return productsCosts;
    }

    public void setProductsCosts(List<CostDTO> productsCosts) {
        this.productsCosts = productsCosts;
        this.productsPurchasedCount = productsCosts.size();
        this.totalCost = 0;
        productsCosts.forEach(pRevenue -> totalCost += pRevenue.getCost());
    }

    public int getProductsPurchasedCount() {
        return productsPurchasedCount;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
