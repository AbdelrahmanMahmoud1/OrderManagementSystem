package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.dto.buying.BuyingProductDTO;

import java.util.List;

public class InventoryStockResponse {
    private List<BuyingProductDTO> products;

    public List<BuyingProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<BuyingProductDTO> products) {
        this.products = products;
    }
}
