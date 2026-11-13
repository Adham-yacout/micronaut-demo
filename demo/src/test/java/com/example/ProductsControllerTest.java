
package com.example;

import com.example.products.model.Product;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class ProductsControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void getAllProducts_shouldReturn200_whenAdminAuthenticated() {

        // 1. Generate a JWT
        HttpResponse<String> tokenResponse =
                client.toBlocking().exchange(
                        HttpRequest.POST("/auth/token", null),
                        String.class
                );

        System.out.println("Token status: " + tokenResponse.getStatus());
        System.out.println("Token body: " + tokenResponse.getBody().orElse("Empty"));
        Map<String, String> tokenMap =
                client.toBlocking().retrieve(
                        HttpRequest.POST("/auth/token", null),
                        Argument.mapOf(String.class, String.class)
                );

        String token = tokenMap.get("access_token");
        // 2. Extract the token


        assertNotNull(token);

        // 3. Call the actual API with the token
        HttpResponse<String> response =
                client.toBlocking().exchange(
                        HttpRequest.GET("/products").bearerAuth(token),
                        String.class
                );

        // 4. Verify HTTP status
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void getAllProducts_shouldReturn401_whenNotAuthenticated() {

        HttpClientResponseException exception =
                assertThrows(
                        HttpClientResponseException.class,
                        () -> client.toBlocking().exchange(
                                HttpRequest.GET("/products"),
                                String.class
                        )
                );

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }
    @Test
    void addProduct_shouldReturn201_whenAdminAuthenticated() {

        // Generate ADMIN token
        Map<String, String> tokenResponse =
                client.toBlocking().retrieve(
                        HttpRequest.POST("/auth/token", null),
                        Argument.mapOf(String.class, String.class)
                );

        String token = tokenResponse.get("access_token");

        // Create product through the API
        HttpResponse<String> response =
                client.toBlocking().exchange(
                        HttpRequest.POST(
                                "/products/add",
                                Map.of(
                                        "name", "Espresso",
                                        "type", "COFFEE"
                                )
                        ).bearerAuth(token),
                        String.class
                );

        assertEquals(HttpStatus.CREATED, response.getStatus());
    }
    @Test
    void updateProduct_shouldReturn200_whenAdminAuthenticated() {

        // Generate ADMIN token
        Map<String, String> tokenResponse =
                client.toBlocking().retrieve(
                        HttpRequest.POST("/auth/token", null),
                        Argument.mapOf(String.class, String.class)
                );

        String token = tokenResponse.get("access_token");

        // Create a product first
        HttpResponse<Product> createdResponse =
                client.toBlocking().exchange(
                        HttpRequest.POST(
                                "/products/add",
                                Map.of("name", "Test Coffee", "type", "COFFEE")
                        ).bearerAuth(token),
                        Product.class
                );

        Integer productId = createdResponse.body().getId();

        // Update the product
        HttpResponse<String> updateResponse =
                client.toBlocking().exchange(
                        HttpRequest.PUT(
                                "/products/" + productId,
                                Map.of("name", "Updated Coffee", "type", "TEA")
                        ).bearerAuth(token),
                        String.class
                );

        assertEquals(HttpStatus.OK, updateResponse.getStatus());
    }

    @Test
    void deleteProduct_shouldReturn204_whenAdminAuthenticated() {

        // Generate ADMIN token
        Map<String, String> tokenResponse =
                client.toBlocking().retrieve(
                        HttpRequest.POST("/auth/token", null),
                        Argument.mapOf(String.class, String.class)
                );

        String token = tokenResponse.get("access_token");

        // Create a product first
        HttpResponse<Product> createdResponse =
                client.toBlocking().exchange(
                        HttpRequest.POST(
                                "/products/add",
                                Map.of("name", "Delete Me", "type", "COFFEE")
                        ).bearerAuth(token),
                        Product.class
                );

        Integer productId = createdResponse.body().getId();

        // Delete the product
        HttpResponse<String> deleteResponse =
                client.toBlocking().exchange(
                        HttpRequest.DELETE("/products/" + productId)
                                .bearerAuth(token),
                        String.class
                );

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatus());
    }
}
