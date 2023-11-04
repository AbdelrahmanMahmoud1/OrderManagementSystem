package com.giza.purshasingmanagement.service.selling;

import com.giza.purshasingmanagement.entity.selling.SellingPurchase;

import java.util.List;

public interface SellingService {

    List<SellingPurchase> findAll();

    SellingPurchase findByPurchaseId(long id);

    SellingPurchase findByOrderId(long id);

    long save(SellingPurchase purchase);
}
