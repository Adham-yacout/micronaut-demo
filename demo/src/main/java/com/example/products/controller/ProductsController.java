package com.example.products.controller;


import com.example.products.model.Product;
import com.example.products.service.ProductService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

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
    public HttpResponse<Product> getproduct(@PathVariable Integer id) {
        return HttpResponse.ok(productService.getProductById(id));
    }
    @Status(HttpStatus.CREATED)
    @Post("/add")
    public HttpResponse<Product> addProduct(@Body @Valid Product product) {
       Product savedProduct = productService.addProduct(product);
        return HttpResponse.created(savedProduct);
    }


    @Delete("{id}")
    public HttpResponse<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return HttpResponse.noContent();
    }

    @Put("{id}")
    public HttpResponse<Product> updateProduct(
            @PathVariable Integer id,
            @Body @Valid Product updatedProduct) {

        return productService.updateProduct(id, updatedProduct)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }
}
