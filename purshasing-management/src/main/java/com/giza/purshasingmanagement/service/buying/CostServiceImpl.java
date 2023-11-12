package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.entity.buying.ProductCost;
import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.repository.buying.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CostServiceImpl implements CostService {

    private final CostRepository costRepository;
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok
    // TODO: 11/11/2023 for constructor autowiring you can do this @RequiredArgsConstructor(onConstructor = @__(@Autowired)) but not needed

    @Autowired
    public CostServiceImpl(CostRepository costRepository) { this.costRepository = costRepository; }


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
