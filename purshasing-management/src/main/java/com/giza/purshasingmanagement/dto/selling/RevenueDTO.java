package com.giza.purshasingmanagement.dto.selling;

import com.giza.purshasingmanagement.entity.selling.ProductRevenue;

import java.io.Serializable;

public class RevenueDTO implements Serializable {

    private String productName;
    private long purchaseCount;
    private long revenue;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getPurchaseCount() {
        return purchaseCount;
    }

    public double increasePurchase(long purchaseCount, double price) {
        this.purchaseCount += purchaseCount;
        double revenue = purchaseCount * price;
        this.revenue += revenue;
        return revenue;
    }

    public long getRevenue() {
        return revenue;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "productName=" + productName +
                ", purchaseCount=" + purchaseCount +
                ", revenue=" + revenue +
                '}';
    }

    static public RevenueDTO entityToDTO(ProductRevenue entity) {
        RevenueDTO dto = new RevenueDTO();
        dto.productName = entity.getProductName();
        dto.purchaseCount = entity.getPurchaseCount();
        dto.revenue = entity.getRevenue();
        return dto;
    }
}
