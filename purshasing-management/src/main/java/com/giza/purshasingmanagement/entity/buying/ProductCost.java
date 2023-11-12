package com.giza.purshasingmanagement.entity.buying;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "product_cost")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ProductCost implements Serializable {
    @Id
    // TODO: 11/11/2023 why is name Id?
    @Column(name = "product_name")
    private String productName;

    @Column(name = "purchase_count")
    private long purchaseCount;

    @Column(name = "cost")
    private long cost;

    public double increasePurchase(long purchaseCount, double price) {
        this.purchaseCount += purchaseCount;
        double cost = purchaseCount * price;
        this.cost += cost;
        return cost;
    }
}
