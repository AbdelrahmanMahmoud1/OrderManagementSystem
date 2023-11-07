package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;

import java.util.List;

public interface BuyingService {

    List<BuyingPurchase> findAll();

    BuyingPurchase findByPurchaseId(long id);

    long save(BuyingPurchase purchase);
}
