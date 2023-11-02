package com.headwayproject.inventoryservice.repository;

import com.headwayproject.inventoryservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product ,Integer> {

    public Product findById(int id);

    Product findByName(String name);
}
