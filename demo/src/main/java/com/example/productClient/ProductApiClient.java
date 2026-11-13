package com.example.productClient;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

import java.util.Map;

@Client("http://localhost:8080")
public interface  ProductApiClient {
    @Get("/products/{id}")
    String getProductById(
            Integer id,
            @Header("Authorization") String authorization
    );

    @Post("/auth/token")
    Map<String, String> generateToken();
}
