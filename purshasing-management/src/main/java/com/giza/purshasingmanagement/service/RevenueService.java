package com.giza.purshasingmanagement.service;

import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.ProductRevenue;

import java.util.List;

public interface RevenueService {

    List<ProductRevenue> findAll();

    ProductRevenue findById(long id);

    double save(Product product);

    void deleteById(long id);
}
