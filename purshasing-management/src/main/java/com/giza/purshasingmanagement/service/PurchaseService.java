package com.giza.purshasingmanagement.service;

import com.giza.purshasingmanagement.entity.Purchase;

import java.util.List;

public interface PurchaseService {

    List<Purchase> findAll();

    Purchase findByPurchaseId(long id);

    Purchase findByOrderId(long id);

    long save(Purchase purchase);
}
