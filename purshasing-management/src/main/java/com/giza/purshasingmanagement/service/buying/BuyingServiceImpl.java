package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;
import com.giza.purshasingmanagement.repository.buying.BuyingRepository;
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
public class BuyingServiceImpl implements BuyingService {

    private final BuyingRepository buyingRepository;

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
