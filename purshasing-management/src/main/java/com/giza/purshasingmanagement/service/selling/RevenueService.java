package com.giza.purshasingmanagement.service.selling;

import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.db.ProductDB;
import com.giza.purshasingmanagement.entity.selling.ProductRevenue;

import java.util.List;

public interface RevenueService {

    List<ProductRevenue> findAll();

    ProductRevenue findById(long id);

    double save(ProductDB product);
}
