package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;
import com.giza.purshasingmanagement.entity.selling.SellingPurchase;
import com.giza.purshasingmanagement.repository.buying.BuyingRepository;
import com.giza.purshasingmanagement.service.selling.SellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuyingServiceImpl implements BuyingService {

    private final BuyingRepository buyingRepository;

    @Autowired
    public BuyingServiceImpl(BuyingRepository buyingRepository) { this.buyingRepository = buyingRepository; }

    @Override
    public List<BuyingPurchase> findAll() {
        return buyingRepository.findAll();
    }

    @Override
    public BuyingPurchase findByPurchaseId(long id) {
        Optional<BuyingPurchase> repoResult = buyingRepository.findById(id);
        BuyingPurchase purchase = null;
        if (repoResult.isPresent())
            purchase = repoResult.get();
        return purchase;
    }

    @Override
    public long save(BuyingPurchase purchase) {
        return buyingRepository.save(purchase).getPurchaseId();
    }
}
