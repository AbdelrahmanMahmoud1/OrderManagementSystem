package com.giza.purshasingmanagement.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String TOPIC_NAME = "purchase";

    @Bean
    public NewTopic purchasingTopic() {
        return TopicBuilder.name(TOPIC_NAME).build();
    }

}
