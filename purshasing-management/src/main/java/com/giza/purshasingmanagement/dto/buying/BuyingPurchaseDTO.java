package com.giza.purshasingmanagement.dto.buying;

import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class BuyingPurchaseDTO implements Serializable {
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok

    private Date purchaseDate;
    private List<Product> products;
    private double cost;

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    static public BuyingPurchaseDTO entityToDTO(BuyingPurchase entity) {
        BuyingPurchaseDTO dto = new BuyingPurchaseDTO();
        dto.purchaseDate = entity.getPurchaseDate();
        dto.cost = entity.getCost();
        dto.products = entity.getProducts();
        return dto;
    }
}
