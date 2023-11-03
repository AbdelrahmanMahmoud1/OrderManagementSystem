package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.entity.buying.ProductCost;
import com.giza.purshasingmanagement.entity.db.ProductDB;
import com.giza.purshasingmanagement.repository.buying.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CostServiceImpl implements CostService {

    private final CostRepository costRepository;

    @Autowired
    public CostServiceImpl(CostRepository costRepository) { this.costRepository = costRepository; }


    @Override
    public List<ProductCost> findAll() {
        return costRepository.findAll();
    }

    @Override
    public ProductCost findById(long id) {
        Optional<ProductCost> repoResult = costRepository.findById(id);
        ProductCost productCost = null;
        if (repoResult.isPresent())
            productCost = repoResult.get();
        return productCost;
    }

    @Override
    public double save(ProductDB product) {
        ProductCost productCost = findById(product.getId());
        if (productCost == null){
            productCost = new ProductCost();
            productCost.setProductId(product.getId());
        }
        double cost = productCost.increasePurchase(product.getQuantity(), product.getPrice());
        costRepository.save(productCost);
        return cost;
    }
}
