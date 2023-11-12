package com.giza.purshasingmanagement.service.selling;

import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.selling.ProductRevenue;
import com.giza.purshasingmanagement.repository.selling.RevenueRepository;
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
public class RevenueServiceImpl implements RevenueService {

    private final RevenueRepository revenueRepository;

    @Override
    public List<ProductRevenue> findAll() {
        return revenueRepository.findAll();
    }

    @Override
    public ProductRevenue findByName(String name) {
        Optional<ProductRevenue> repoResult = revenueRepository.findById(name);
        ProductRevenue productRevenue = null;
        if (repoResult.isPresent())
            productRevenue = repoResult.get();
        return productRevenue;
    }

    @Override
    public double save(Product product) {
        ProductRevenue productRevenue = findByName(product.getName());
        if (productRevenue == null){
            productRevenue = new ProductRevenue();
            productRevenue.setProductName(product.getName());
        }
        double revenue = productRevenue.increasePurchase(product.getQuantity(), product.getPrice());
        revenueRepository.save(productRevenue);
        return revenue;
    }
}
