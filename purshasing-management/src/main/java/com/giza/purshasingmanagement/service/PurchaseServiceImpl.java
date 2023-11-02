package com.giza.purshasingmanagement.service;

import com.giza.purshasingmanagement.entity.Purchase;
import com.giza.purshasingmanagement.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository) { this.purchaseRepository = purchaseRepository; }

    @Override
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    @Override
    public Purchase findByPurchaseId(long id) {
        Optional<Purchase> repoResult = purchaseRepository.findById(id);
        Purchase purchase = null;
        if (repoResult.isPresent())
            purchase = repoResult.get();
        return purchase;
    }

    @Override
    public Purchase findByOrderId(long id) {
        if (id <= 0)
            return null;
        List<Purchase> purchaseList = findAll();
        Purchase purchase = null;
        for (Purchase p : purchaseList)
            if (p.getOrderId() == id) {
                purchase = p;
                break;
            }
        return purchase;
    }

    @Override
    public long save(Purchase purchase) {
        return purchaseRepository.save(purchase).getPurchaseId();
    }
}
