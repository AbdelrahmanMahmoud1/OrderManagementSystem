package com.giza.purshasingmanagement.controller.selling.response;

import com.giza.purshasingmanagement.entity.selling.ProductRevenue;

import java.util.List;

public class RevenueSummaryResponse {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok

    private List<ProductRevenue> productsRevenues;
    private int productsPurchasedCount;
    private double totalRevenue;

    public List<ProductRevenue> getProductsRevenues() {
        return productsRevenues;
    }

    public void setProductsRevenues(List<ProductRevenue> productsRevenues) {
        this.productsRevenues = productsRevenues;
        this.productsPurchasedCount = productsRevenues.size();
        this.totalRevenue = 0;
        productsRevenues.forEach(pRevenue -> totalRevenue += pRevenue.getRevenue());
    }

    public int getProductsPurchasedCount() {
        return productsPurchasedCount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}
