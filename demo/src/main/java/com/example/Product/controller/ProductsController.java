package com.example.Product.controller;


import com.example.Product.model.Product;
import com.example.Product.service.ProductService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;

import java.util.List;
import java.util.Optional;

@Controller("/products")
public class ProductsController {

    private  final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }


    @Get
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts() ;
    }

    @Get("{id}")
    public HttpResponse<Product> getproduct(@PathVariable Integer id)
    {
        return productService.getProductById(id).map(HttpResponse::ok).orElse(HttpResponse.notFound());
    }

    @Status(HttpStatus.CREATED)
    @Post("/add")
    public HttpResponse<Product> addProduct(@Body Product product) {
       Product savedProduct = productService.addProduct(product);
        return HttpResponse.created(savedProduct);
    }


    @Delete("{id}")
    public HttpResponse<Void> deleteProduct(@PathVariable Integer id) {
        if (productService.getProductById(id).isEmpty()) {
            return HttpResponse.notFound();
        }

        productService.deleteProduct(id);
        return HttpResponse.noContent();
    }

    @Put("{id}")
    public HttpResponse<Product> updateProduct(
            @PathVariable Integer id,
            @Body Product updatedProduct) {

        return productService.updateProduct(id, updatedProduct)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }
}
