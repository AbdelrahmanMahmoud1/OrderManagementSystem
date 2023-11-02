package com.giza.purshasingmanagement.entity.selling;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "product_revenue")
public class ProductRevenue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long productId;

    @Column(name = "purchase_count")
    private long purchaseCount;

    @Column(name = "revenue")
    private long revenue;

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
        this.revenue += revenue;
        return revenue;
    }

    public long getRevenue() {
        return revenue;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "productId=" + productId +
                ", purchaseCount=" + purchaseCount +
                ", revenue=" + revenue +
                '}';
    }
}
