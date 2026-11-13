package com.example.products.messages;

import com.example.productClient.ProductApiClient;
import com.example.products.service.ProductService;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@KafkaListener(groupId = "product-service-group")
@Slf4j
public class ProductEventConsumer {
    private final ProductApiClient productApiClient;

    public ProductEventConsumer(ProductApiClient productApiClient) {
        this.productApiClient = productApiClient;
    }
    private static final Logger LOG =
            LoggerFactory.getLogger(ProductService.class);


    @Topic("product-events")
    public void receive(String message) {
        System.out.println("Received Kafka event: " + message);
LOG.info("alo ana consumer Received Kafka event:  "+ message);
        Map<String, String> tokenResponse =
                productApiClient.generateToken();

        String token = tokenResponse.get("access_token");
        int id = Integer.parseInt(
                message.split("ID=")[1].split(",")[0]
        );
        String response = productApiClient.getProductById(id,"Bearer " + token);

        LOG.info("GET /products response: {}", response);
    }
}