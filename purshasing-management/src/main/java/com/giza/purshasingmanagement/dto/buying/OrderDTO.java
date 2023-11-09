package com.giza.purshasingmanagement.dto.buying;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO implements Serializable {

    private long id;
    private LocalDateTime purchaseTime;
    private List<BuyingProductDTO> products;

    public OrderDTO() {
        products = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<BuyingProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<BuyingProductDTO> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", purchaseTime=" + purchaseTime +
                ", products=" + products +
                '}';
    }
}
