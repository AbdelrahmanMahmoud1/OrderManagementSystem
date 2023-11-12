package com.giza.purshasingmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private String name;
    private int quantity;
    private float price;
}
