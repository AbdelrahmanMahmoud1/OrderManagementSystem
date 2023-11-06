package com.project.orderservice.responses;

import com.project.orderservice.model.OrderLineItems;

import java.util.List;

public class InventoryResponse {
    private List<OrderLineItems> products;

    public List<OrderLineItems> getProducts() {
        return products;
    }

    public void setProducts(List<OrderLineItems> products) {
        this.products = products;
    }
}
