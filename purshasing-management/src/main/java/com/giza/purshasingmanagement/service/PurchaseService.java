package com.giza.purshasingmanagement.service;

import com.giza.purshasingmanagement.entity.Purchase;

import java.util.List;

public interface PurchaseService {

    List<Purchase> findAll();

    Purchase findById(long id);

    long save(Purchase purchase);

    void deleteById(long id);
}
