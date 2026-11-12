package com.example.Product.service;


import com.example.Product.model.Product;
import com.example.Product.repository.ProductRepository;
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

    public Optional<Product> getProductById(Integer id)
    {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product)
    {
        return productRepository.save(product);
    }

    public  void  deleteProduct(Integer id)
    {
        productRepository.deleteById(id);
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
