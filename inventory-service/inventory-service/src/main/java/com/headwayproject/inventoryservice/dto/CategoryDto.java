package com.headwayproject.inventoryservice.dto;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private int id;

    private String name;
    public CategoryDto(String name) {
        this.name = name;
    }
}
