package com.giza.purshasingmanagement.controller.response;

import com.giza.purshasingmanagement.entity.ProductRevenue;

import java.util.List;

public class RevenueSummaryResponse {

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
