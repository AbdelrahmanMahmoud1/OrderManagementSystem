package com.giza.purshasingmanagement.entity.selling;

import com.giza.purshasingmanagement.entity.db.ProductDB;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "selling-purchase")
public class SellingPurchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "purchase_id")
    private long purchaseId;

    @Column(name = "order_id")
    private long orderId;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "products")
    @ElementCollection(targetClass = ProductDB.class)
    private List<ProductDB> products;

    @Column(name = "revenue")
    private double revenue;

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<ProductDB> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDB> products) {
        this.products = products;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
