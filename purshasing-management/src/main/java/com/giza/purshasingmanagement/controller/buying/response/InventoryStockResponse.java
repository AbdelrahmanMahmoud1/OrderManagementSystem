package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.entity.Product;

import java.util.List;

//TODO: Should be used when getting a response from the inventory
public class InventoryStockResponse {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
