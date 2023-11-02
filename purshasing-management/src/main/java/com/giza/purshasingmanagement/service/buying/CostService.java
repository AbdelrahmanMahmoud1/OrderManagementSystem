package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.buying.ProductCost;

import java.util.List;

public interface CostService {

    List<ProductCost> findAll();

    ProductCost findById(long id);

    double save(Product product);
}
