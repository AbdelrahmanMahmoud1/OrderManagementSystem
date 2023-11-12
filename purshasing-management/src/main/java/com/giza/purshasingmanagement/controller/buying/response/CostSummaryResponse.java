package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.dto.buying.CostDTO;

import java.util.List;

public class CostSummaryResponse {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok

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
