package com.giza.purshasingmanagement.dto.selling;

import com.giza.purshasingmanagement.entity.Product;

import java.io.Serializable;

public class SellingProductDTO implements Serializable {
    private String name;
    private int quantity;
    private float price;

    SellingProductDTO() {
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

    @Override
    public String toString() {
        return "Product{" +
                "name=" + name +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    static public SellingProductDTO entityToDTO(Product entity) {
        SellingProductDTO dto = new SellingProductDTO();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.quantity = entity.getQuantity();
        return dto;
    }
}
