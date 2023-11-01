package com.giza.purshasingmanagement.repository;

import com.giza.purshasingmanagement.entity.ProductRevenue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevenueRepository extends JpaRepository<ProductRevenue, Long> {
}
