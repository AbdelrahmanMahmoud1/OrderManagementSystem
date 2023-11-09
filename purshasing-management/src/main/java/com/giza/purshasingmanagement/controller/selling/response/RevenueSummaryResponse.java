package com.giza.purshasingmanagement.controller.selling.response;

import com.giza.purshasingmanagement.dto.selling.RevenueDTO;

import java.util.List;

public class RevenueSummaryResponse {

    private List<RevenueDTO> productsRevenues;
    private int productsPurchasedCount;
    private double totalRevenue;

    public List<RevenueDTO> getProductsRevenues() {
        return productsRevenues;
    }

    public void setProductsRevenues(List<RevenueDTO> productsRevenues) {
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
