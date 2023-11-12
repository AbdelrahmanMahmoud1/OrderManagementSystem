package com.giza.purshasingmanagement.dto.buying;

import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class BuyingPurchaseDTO implements Serializable {
    private Date purchaseDate;
    private List<Product> products;
    private double cost;

    static public BuyingPurchaseDTO entityToDTO(BuyingPurchase entity) {
        BuyingPurchaseDTO dto = new BuyingPurchaseDTO();
        dto.purchaseDate = entity.getPurchaseDate();
        dto.cost = entity.getCost();
        dto.products = entity.getProducts();
        return dto;
    }
}
