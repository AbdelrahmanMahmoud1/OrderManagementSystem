package com.programming.notificationservice;

import config.KafkaConsumerConfig;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j //message received inside the notification service
public class NotificationServiceApplication {

	private final EmailNotificationService emailNotificationService;

	public NotificationServiceApplication(EmailNotificationService emailNotificationService) {
		this.emailNotificationService = emailNotificationService;
	}


	public static void main(String[] args) {

		SpringApplication.run(NotificationServiceApplication.class, args);
	}
	@KafkaListener(
			topics = "notificationTopic",
			groupId = "notification-consumer-group",
			containerFactory = "kafkaListenerContainerFactory"
	)
	public void HandleNotification(OrderPlacedEvent orderPlacedEvent) throws MessagingException {

		String orderNumber = orderPlacedEvent.getOrderNumber();
		String recipientEmail = "recipient mail";  // Set the recipient's email address


		// Send an email notification
		emailNotificationService.sendOrderConfirmationEmail(recipientEmail, orderNumber);
		try {
			emailNotificationService.sendOrderConfirmationEmail(recipientEmail, orderNumber);
			log.info("Received Notification for Order - {}", orderNumber);
		} catch (MessagingException e) {
			log.error("Error sending email notification: " + e.getMessage());
		}

	}


	//	log.info("Received Notification for Order- {}",orderPlacedEvent.getOrderNumber());
		//printing el message el gaya mn order

	@Bean
	public KafkaConsumerConfig kafkaConsumerConfig() {
		return new KafkaConsumerConfig();

	}
}
