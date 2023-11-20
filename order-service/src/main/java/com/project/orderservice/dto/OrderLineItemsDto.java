package com.project.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderLineItemsDto {
    private int id;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    public OrderLineItemsDto(String name, BigDecimal price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
