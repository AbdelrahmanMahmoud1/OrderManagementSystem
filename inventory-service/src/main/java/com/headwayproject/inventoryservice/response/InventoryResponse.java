package com.headwayproject.inventoryservice.response;

import com.headwayproject.inventoryservice.entity.Product;

import java.util.List;

public class InventoryResponse {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
