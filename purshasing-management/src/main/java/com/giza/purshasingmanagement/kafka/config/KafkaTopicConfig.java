package com.giza.purshasingmanagement.kafka.config;

import com.giza.purshasingmanagement.AppConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic purchasingTopic() {
        return TopicBuilder.name(AppConstants.TOPIC_NAME).build();
    }

}
