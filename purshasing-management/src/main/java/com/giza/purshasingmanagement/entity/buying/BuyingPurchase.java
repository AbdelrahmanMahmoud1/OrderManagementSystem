package com.giza.purshasingmanagement.entity.buying;

import com.giza.purshasingmanagement.entity.Product;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "buying-purchase")
public class BuyingPurchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "purchase_id")
    private long purchaseId;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "products")
    @ElementCollection(targetClass = Product.class)
    private List<Product> products;

    @Column(name = "cost")
    private double cost;

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
