package com.giza.purshasingmanagement.kafka;

import com.giza.purshasingmanagement.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer<T> {
    @Autowired
    private KafkaTemplate<String, T> kafkaTemplate;

    public void sendMessage(T data){

        Message<T> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, AppConstants.TOPIC_NAME)
                .build();

        kafkaTemplate.send(message);
    }
}
