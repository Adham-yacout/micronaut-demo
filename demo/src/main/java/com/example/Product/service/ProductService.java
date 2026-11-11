package com.example.Product.service;


import com.example.Product.model.Product;
import com.example.Product.repository.data.InMemoryStore;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ProductService {

    private final InMemoryStore store;
    public ProductService(InMemoryStore store){
        this.store=store;
    }

    public List<Product>  getAllProducts(){
        return new ArrayList<>(store.getProducts().values());
    }

    public Product getProductById(Integer id)
    {
        return store.getProducts().get(id);
    }

    public Product addProduct(Product product)
    {
        return store.addProduct(product);
    }
}
