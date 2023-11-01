package com.giza.purshasingmanagement.service;

import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.Purchase;

import java.util.List;

public interface PurchaseService {

    List<Purchase> findAll();

    Purchase findById(long id);

    double save(Product product);

    void deleteById(long id);
}
