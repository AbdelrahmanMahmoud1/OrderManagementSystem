package com.headwayproject.inventoryservice.response;

import com.headwayproject.inventoryservice.entity.Product;

import java.util.List;

public class InventoryResponse {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
