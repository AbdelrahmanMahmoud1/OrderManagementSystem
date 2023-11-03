package com.giza.purshasingmanagement.entity.db;

import java.io.Serializable;

public class ProductDB implements Serializable {

    private int id;
    private int quantity;
    private float price;

    public ProductDB(){

    }

    public ProductDB(int id, int quantity, float price) {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
