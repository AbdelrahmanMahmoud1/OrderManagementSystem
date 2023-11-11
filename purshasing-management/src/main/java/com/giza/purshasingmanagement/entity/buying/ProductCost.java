package com.giza.purshasingmanagement.entity.buying;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "product_cost")
public class ProductCost implements Serializable {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok

    @Id
    // TODO: 11/11/2023 why is name Id?
    @Column(name = "product_name")
    private String productName;

    @Column(name = "purchase_count")
    private long purchaseCount;

    @Column(name = "cost")
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

    public long getRevenue() {
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
}
