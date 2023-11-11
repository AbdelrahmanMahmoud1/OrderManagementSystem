package com.headwayproject.inventoryservice.repository;

import com.headwayproject.inventoryservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product ,Integer> {
    // TODO: 11/11/2023 return optional

    public Product findById(int id);
    // TODO: 11/11/2023 return optional

    Product findByName(String name);
}
