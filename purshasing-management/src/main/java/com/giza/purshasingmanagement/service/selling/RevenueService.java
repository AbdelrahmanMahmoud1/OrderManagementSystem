package com.giza.purshasingmanagement.service.selling;

import com.giza.purshasingmanagement.entity.db.ProductDB;
import com.giza.purshasingmanagement.entity.selling.ProductRevenue;

import java.util.List;

public interface RevenueService {

    List<ProductRevenue> findAll();

    ProductRevenue findByName(String name);

    double save(ProductDB product);
}
