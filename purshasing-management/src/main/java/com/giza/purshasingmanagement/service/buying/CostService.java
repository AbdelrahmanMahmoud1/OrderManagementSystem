package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.entity.buying.ProductCost;
import com.giza.purshasingmanagement.entity.Product;

import java.util.List;

public interface CostService {

    List<ProductCost> findAll();

    ProductCost findByName(String id);

    double save(Product product);
}
