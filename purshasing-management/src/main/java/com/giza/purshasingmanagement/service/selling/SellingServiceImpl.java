package com.giza.purshasingmanagement.service.selling;

import com.giza.purshasingmanagement.entity.selling.SellingPurchase;
import com.giza.purshasingmanagement.repository.selling.SellingRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class SellingServiceImpl implements SellingService {

    private final SellingRepository sellingRepository;

    @Override
    public List<SellingPurchase> findAll() {
        return sellingRepository.findAll();
    }

    @Override
    public SellingPurchase findByPurchaseId(long id) {
        Optional<SellingPurchase> repoResult = sellingRepository.findById(id);
        SellingPurchase purchase = null;
        if (repoResult.isPresent())
            purchase = repoResult.get();
        return purchase;
    }

    @Override
    public SellingPurchase findByOrderId(long id) {
        if (id <= 0)
            return null;
        List<SellingPurchase> purchaseList = findAll();
        SellingPurchase purchase = null;
        for (SellingPurchase p : purchaseList)
            if (p.getOrderId() == id) {
                purchase = p;
                break;
            }
        return purchase;
    }

    @Override
    public long save(SellingPurchase purchase) {
        return sellingRepository.save(purchase).getPurchaseId();
    }
}
