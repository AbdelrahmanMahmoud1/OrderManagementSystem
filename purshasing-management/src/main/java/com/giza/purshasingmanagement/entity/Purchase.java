package com.giza.purshasingmanagement.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long productId;

    @Column(name = "purchase_count")
    private long purchaseCount;

    @Column(name = "product_revenue")
    private long productRevenue;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getPurchaseCount() {
        return purchaseCount;
    }

    public double increasePurchase(long purchaseCount, double price) {
        this.purchaseCount += purchaseCount;
        double revenue = purchaseCount * price;
        this.productRevenue += revenue;
        return revenue;
    }

    public long getProductRevenue() {
        return productRevenue;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "productId=" + productId +
                ", purchaseCount=" + purchaseCount +
                ", productRevenue=" + productRevenue +
                '}';
    }
}
