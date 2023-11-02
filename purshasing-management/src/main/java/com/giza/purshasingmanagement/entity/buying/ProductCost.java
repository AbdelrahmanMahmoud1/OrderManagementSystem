package com.giza.purshasingmanagement.entity.buying;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "product_cost")
public class ProductCost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long productId;

    @Column(name = "purchase_count")
    private long purchaseCount;

    @Column(name = "cost")
    private long cost;

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
        double cost = purchaseCount * price;
        this.cost += cost;
        return cost;
    }

    public long getRevenue() {
        return cost;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "productId=" + productId +
                ", purchaseCount=" + purchaseCount +
                ", cost=" + cost +
                '}';
    }
}
