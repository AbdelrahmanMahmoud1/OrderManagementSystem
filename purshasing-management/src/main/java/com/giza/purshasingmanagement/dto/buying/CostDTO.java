package com.giza.purshasingmanagement.dto.buying;

import com.giza.purshasingmanagement.entity.buying.ProductCost;

import java.io.Serializable;

public class CostDTO implements Serializable {

    private String productName;
    private long purchaseCount;
    private long cost;

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
        double cost = purchaseCount * price;
        this.cost += cost;
        return cost;
    }

    public long getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "productName=" + productName +
                ", purchaseCount=" + purchaseCount +
                ", cost=" + cost +
                '}';
    }

    static public CostDTO entityToDTO(ProductCost entity) {
        CostDTO dto = new CostDTO();
        dto.productName = entity.getProductName();
        dto.purchaseCount = entity.getPurchaseCount();
        dto.cost = entity.getCost();
        return dto;
    }
}
