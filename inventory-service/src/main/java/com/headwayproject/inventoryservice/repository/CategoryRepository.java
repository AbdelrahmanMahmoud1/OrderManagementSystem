package com.headwayproject.inventoryservice.repository;

import com.headwayproject.inventoryservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    // TODO: 11/11/2023 return optional
    public Category findByName(String name);
    // TODO: 11/11/2023 return optional

    public Category findById(int id);
}
