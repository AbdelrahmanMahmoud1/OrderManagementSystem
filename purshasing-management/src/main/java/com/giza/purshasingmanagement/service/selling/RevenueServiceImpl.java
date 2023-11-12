package com.giza.purshasingmanagement.service.selling;

import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.selling.ProductRevenue;
import com.giza.purshasingmanagement.repository.selling.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RevenueServiceImpl implements RevenueService {

    private final RevenueRepository revenueRepository;
    // TODO: 11/11/2023 use lombok @Getter and @Setter and @RequiredArgsConstructor https://www.baeldung.com/intro-to-project-lombok
    // TODO: 11/11/2023 for constructor autowiring you can do this @RequiredArgsConstructor(onConstructor = @__(@Autowired)) but not needed

    @Autowired
    public RevenueServiceImpl(RevenueRepository revenueRepository) { this.revenueRepository = revenueRepository; }


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
