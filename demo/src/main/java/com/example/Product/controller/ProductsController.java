package com.example.Product.controller;


import com.example.Product.model.Product;
import com.example.Product.repository.data.InMemoryStore;
import com.example.Product.service.ProductService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;

import java.util.ArrayList;
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
    public Product getproduct(@PathVariable Integer id)
    {
        return productService.getProductById(id);
    }

    @Status(HttpStatus.CREATED)
    @Post("/add")
    public Product addProduct(@Body Product product) {
        return productService.addProduct();
    }

//    @Delete("{id}")
//    public void Deleteid(@PathVariable Integer id)
//    {
//        if(productService.getProductById(id)!=null)
//        {
//            productService.deleteProduct(id);
//        }else{
//            throw new HttpStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Product with id " + id + " not found"
//            );
//        }
//
//    }
}
