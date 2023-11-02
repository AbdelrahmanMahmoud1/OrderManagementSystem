package com.giza.purshasingmanagement.repository;

import com.giza.purshasingmanagement.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
