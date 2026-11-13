package com.example.products.messages;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface ProductEventProducer {

    @Topic("product-events")
    void send(String message);
}