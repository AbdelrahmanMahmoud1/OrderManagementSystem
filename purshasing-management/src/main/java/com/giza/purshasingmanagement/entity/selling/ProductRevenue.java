package com.giza.purshasingmanagement.entity.selling;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "product_revenue")
public class ProductRevenue implements Serializable {

    @Id
    @Column(name = "product_name")
    private String productName;

    @Column(name = "purchase_count")
    private long purchaseCount;

    @Column(name = "revenue")
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
}
