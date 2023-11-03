package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.entity.Product;

import java.util.List;

public class InventoryStockResponse {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
