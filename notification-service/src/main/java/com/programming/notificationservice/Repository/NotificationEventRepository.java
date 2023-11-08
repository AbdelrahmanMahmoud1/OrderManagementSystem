package com.programming.notificationservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.programming.notificationservice.OrderPlacedEvent;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationEventRepository extends JpaRepository<OrderPlacedEvent, Long>{
}