package com.giza.purshasingmanagement.service.selling;

import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.selling.ProductRevenue;

import java.util.List;

public interface RevenueService {

    List<ProductRevenue> findAll();

    ProductRevenue findByName(String name);

    double save(Product product);
}
