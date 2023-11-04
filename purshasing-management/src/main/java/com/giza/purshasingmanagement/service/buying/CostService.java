package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.entity.buying.ProductCost;
import com.giza.purshasingmanagement.entity.db.ProductDB;

import java.util.List;

public interface CostService {

    List<ProductCost> findAll();

    ProductCost findById(long id);

    double save(ProductDB product);
}
