package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.entity.buying.ProductCost;

import java.util.List;

public class CostSummaryResponse {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok

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
