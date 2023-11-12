package com.project.orderservice.responses;

import com.project.orderservice.model.OrderLineItems;

import java.util.List;

public class InventoryResponse {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok

    private List<OrderLineItems> products;

    public List<OrderLineItems> getProducts() {
        return products;
    }

    public void setProducts(List<OrderLineItems> products) {
        this.products = products;
    }
}
