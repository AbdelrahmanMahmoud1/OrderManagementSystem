package com.gizasystems.notificationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    // TODO: 11/12/2023 nice to have use constructor injection with @RequiredArgsConstructor
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String toEmail, String body){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ecommercegiza@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject("Your New Order From Giza Ecommerce!");

        mailSender.send(message);
    }

}
