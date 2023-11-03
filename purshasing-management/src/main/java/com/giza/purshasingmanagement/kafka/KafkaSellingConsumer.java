package com.giza.purshasingmanagement.kafka;

import com.giza.purshasingmanagement.AppConstants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaSellingConsumer {
    @KafkaListener(topics = AppConstants.TOPIC_NAME, groupId = "groupId")
    void consumer(Map<Long, Double> data) {
        System.out.println("Message received: " + data);
    }
}
