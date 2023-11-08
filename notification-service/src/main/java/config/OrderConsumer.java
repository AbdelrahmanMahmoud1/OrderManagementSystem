package config;

import com.programming.notificationservice.OrderPlacedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service

public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);
@KafkaListener(topics = "${spring.kafka.topic.name}"
,groupId = "${spring.kafka.consumer.group-id}")

    public void consume(OrderPlacedEvent event){
    LOGGER.info(String.format("Order event recieved => %s", event.toString()));


    }
}
