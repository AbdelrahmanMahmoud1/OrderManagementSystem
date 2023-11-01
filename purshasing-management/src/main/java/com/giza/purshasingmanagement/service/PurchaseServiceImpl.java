package com.giza.purshasingmanagement.service;

import com.giza.purshasingmanagement.entity.Product;
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
    public Purchase findById(long id) {
        Optional<Purchase> repoResult = purchaseRepository.findById(id);
        Purchase purchase = null;
        if (repoResult.isPresent())
            purchase = repoResult.get();
        return purchase;
    }

    @Override
    public double save(Product product) {
        Purchase purchase = findById(product.getId());
        if (purchase == null){
            purchase = new Purchase();
            purchase.setProductId(product.getId());
        }
        double revenue = purchase.increasePurchase(product.getQuantity(), product.getPrice());
        purchaseRepository.save(purchase);
        return revenue;
    }

    @Override
    public void deleteById(long id) {
        purchaseRepository.deleteById(id);
    }
}
