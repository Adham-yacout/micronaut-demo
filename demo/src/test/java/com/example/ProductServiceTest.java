
package com.example;

import com.example.products.exception.ProductNotFoundException;
import com.example.products.model.Product;
import com.example.products.repository.ProductRepository;
import com.example.products.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void getProductById_shouldReturnProduct_whenProductExists() {

        // Arrange
        Product product = new Product(
                1,
                "Latte",
                Product.Type.COFFEE
        );

        when(productRepository.findById(1))
                .thenReturn(Optional.of(product));

        // Act
        Product result = productService.getProductById(1);

        // Assert
        assertEquals(1, result.getId());
        assertEquals("Latte", result.getName());
        assertEquals(Product.Type.COFFEE, result.getType());
    }


    @Test
    void getProductById_shouldThrowException_whenProductDoesNotExist() {

        // Arrange
        when(productRepository.findById(99))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(99)
        );
    }

    @Test
    void addProduct_shouldReturnSavedProduct() {

        // Arrange
        Product product = new Product(
                null,
                "Cappuccino",
                Product.Type.COFFEE
        );

        Product savedProduct = new Product(
                2,
                "Cappuccino",
                Product.Type.COFFEE
        );

        when(productRepository.save(product))
                .thenReturn(savedProduct);

        // Act
        Product result = productService.addProduct(product);

        // Assert
        assertEquals(2, result.getId());
        assertEquals("Cappuccino", result.getName());
        assertEquals(Product.Type.COFFEE, result.getType());
    }
}
