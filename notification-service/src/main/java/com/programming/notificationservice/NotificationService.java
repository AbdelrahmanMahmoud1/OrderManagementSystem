package com.programming.notificationservice;

import com.programming.notificationservice.Repository.NotificationEventRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final EmailNotificationService emailNotificationService;
    private final NotificationEventRepository notificationEventRepository;

    public NotificationService(EmailNotificationService emailNotificationService, NotificationEventRepository notificationEventRepository) {
        this.emailNotificationService = emailNotificationService;
        this.notificationEventRepository = notificationEventRepository;
    }

    public void saveNotificationEvent(OrderPlacedEvent event) {
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
        orderPlacedEvent.setOrderNumber(event.getOrderNumber());
        orderPlacedEvent.setStatus(event.getStatus());
        orderPlacedEvent.setMessage(event.getMessage());
        notificationEventRepository.save(orderPlacedEvent);
    }
}



