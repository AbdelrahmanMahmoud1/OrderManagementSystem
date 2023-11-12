package com.giza.purshasingmanagement.dto.buying;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class BuyingProductDTO implements Serializable {
    private int id;
    private String name;
    private int quantity;
    private float price;
    private CategoryDTO category;
}
