package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.entity.buying.ProductCost;
import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.repository.buying.CostRepository;
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
public class CostServiceImpl implements CostService {

    private final CostRepository costRepository;

    @Override
    public List<ProductCost> findAll() {
        return costRepository.findAll();
    }

    @Override
    public ProductCost findByName(String id) {
        Optional<ProductCost> repoResult = costRepository.findById(id);
        ProductCost productCost = null;
        if (repoResult.isPresent())
            productCost = repoResult.get();
        return productCost;
    }

    @Override
    public double save(Product product) {
        ProductCost productCost = findByName(product.getName());
        if (productCost == null){
            productCost = new ProductCost();
            productCost.setProductName(product.getName());
        }
        double cost = productCost.increasePurchase(product.getQuantity(), product.getPrice());
        costRepository.save(productCost);
        return cost;
    }
}
