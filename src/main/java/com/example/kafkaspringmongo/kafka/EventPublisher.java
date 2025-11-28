package com.example.kafkaspringmongo.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public static final String TOPIC = "orders";

    public EventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String key, String json) {
        kafkaTemplate.send(TOPIC, key, json);
    }
}
