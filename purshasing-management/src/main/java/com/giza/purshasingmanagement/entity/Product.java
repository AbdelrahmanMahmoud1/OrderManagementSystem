package com.giza.purshasingmanagement.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private int quantity;
    private float price;

    Product() {
    }

    public Product(int id, int quantity, float price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
