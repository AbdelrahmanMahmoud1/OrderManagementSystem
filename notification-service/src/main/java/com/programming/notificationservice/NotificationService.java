package com.programming.notificationservice;

import org.springframework.stereotype.Service;
import com.programming.notificationservice.EmailNotificationService;

@Service
public class NotificationService {
    private final EmailNotificationService emailNotificationService;

    public NotificationService(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

}
