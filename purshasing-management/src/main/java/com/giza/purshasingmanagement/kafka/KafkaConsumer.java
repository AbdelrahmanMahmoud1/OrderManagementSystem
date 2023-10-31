package com.giza.purshasingmanagement.kafka;

import com.giza.purshasingmanagement.AppConstants;
import com.giza.purshasingmanagement.entity.Purchase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = AppConstants.TOPIC_NAME, groupId = "groupId")
    void consumer(Purchase data) {
        System.out.println("Message received: " + data);
    }
}
