package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.dto.buying.BuyingProductDTO;

import java.util.List;

public class InventoryStockResponse {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok
    private List<BuyingProductDTO> products;

    public List<BuyingProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<BuyingProductDTO> products) {
        this.products = products;
    }
}
