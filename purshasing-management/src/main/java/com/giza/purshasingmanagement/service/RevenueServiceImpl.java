package com.giza.purshasingmanagement.service;

import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.ProductRevenue;
import com.giza.purshasingmanagement.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RevenueServiceImpl implements RevenueService {

    private final RevenueRepository revenueRepository;

    @Autowired
    public RevenueServiceImpl(RevenueRepository revenueRepository) { this.revenueRepository = revenueRepository; }


    @Override
    public List<ProductRevenue> findAll() {
        return revenueRepository.findAll();
    }

    @Override
    public ProductRevenue findById(long id) {
        Optional<ProductRevenue> repoResult = revenueRepository.findById(id);
        ProductRevenue productRevenue = null;
        if (repoResult.isPresent())
            productRevenue = repoResult.get();
        return productRevenue;
    }

    @Override
    public double save(Product product) {
        ProductRevenue productRevenue = findById(product.getId());
        if (productRevenue == null){
            productRevenue = new ProductRevenue();
            productRevenue.setProductId(product.getId());
        }
        double revenue = productRevenue.increasePurchase(product.getQuantity(), product.getPrice());
        revenueRepository.save(productRevenue);
        return revenue;
    }

    @Override
    public void deleteById(long id) {
        revenueRepository.deleteById(id);
    }
}
