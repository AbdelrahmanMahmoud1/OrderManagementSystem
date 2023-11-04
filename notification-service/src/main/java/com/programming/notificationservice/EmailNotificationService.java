package com.programming.notificationservice;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;

public class EmailNotificationService {


    private final JavaMailSender javaMailSender;

    public EmailNotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendOrderConfirmationEmail(String recipient, String orderNumber) throws jakarta.mail.MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        // Set the recipient email address
        helper.setTo(recipient);

        // Set the subject of the email
        helper.setSubject("Order Confirmation");

        // Set the content of the email
        String emailContent = "Your order with order number " + orderNumber + " has been confirmed.";
        helper.setText(emailContent, true); // 'true' indicates HTML content

        // Send the email
        javaMailSender.send(mimeMessage);

    }

}
