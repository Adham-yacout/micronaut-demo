package com.example.products.service;


import com.example.products.exception.ProductNotFoundException;
import com.example.products.model.Product;
import com.example.products.repository.ProductRepository;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class ProductService {

    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public List<Product>  getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product addProduct(Product product)
    {
        return productRepository.save(product);
    }

    public  void  deleteProduct(Integer id)
    {
       Product product= productRepository.findById(id).orElseThrow( ()-> new ProductNotFoundException(id));
        productRepository.delete(product);
    }

    public Optional<Product> updateProduct(Integer id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setType(updatedProduct.getType());

                    return productRepository.update(existingProduct);
                });
    }
}
