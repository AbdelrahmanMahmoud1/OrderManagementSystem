package com.giza.purshasingmanagement.dto.buying;

import java.io.Serializable;

public class BuyingProductDTO implements Serializable {
    private int id;
    private String name;
    private int quantity;
    private float price;
    private CategoryDTO category;

    BuyingProductDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name= " + name +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
