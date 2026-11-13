package com.example.products.service;


import com.example.products.exception.ProductNotFoundException;
import com.example.products.messages.ProductEventProducer;
import com.example.products.model.Product;
import com.example.products.repository.ProductRepository;
import io.micronaut.cache.annotation.CacheInvalidate;
import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.util.List;
import java.util.Optional;

@Singleton
@Slf4j
public class ProductService {

    private static final Logger LOG =
            LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductEventProducer productEventProducer;
    public ProductService(ProductRepository productRepository ,  ProductEventProducer productEventProducer){
        this.productRepository=productRepository;
        this.productEventProducer=productEventProducer;
    }


    public List<Product>  getAllProducts(){

        return productRepository.findAll();
    }

    @Cacheable("product-cache")
    public Product getProductById(Integer id) {
        LOG.info("ana bageb product {}",id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product addProduct(Product product)
    {
        Product savedProduct = productRepository.save(product);
        LOG.info("Product created successfully: {}", savedProduct.getId());
        LOG.info("sending message to product-events :");
        productEventProducer.send(
                "Product created: ID=" + savedProduct.getId()
                        + ", Name=" + savedProduct.getName()
        );
        LOG.info("Product created: ID=" + savedProduct.getId()
                + ", Name=" + savedProduct.getName());


        return savedProduct;
    }
    @CacheInvalidate(cacheNames = "product-cache", all = true)
    public  void  deleteProduct(Integer id)
    {
        LOG.info("trying to delete product with id: {}",id );
       Product product= productRepository.findById(id).orElseThrow( ()-> new ProductNotFoundException(id));
        productRepository.delete(product);
    }

    @CacheInvalidate(cacheNames = "product-cache", all = true)
    public Optional<Product> updateProduct(Integer id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setType(updatedProduct.getType());

                    return productRepository.update(existingProduct);
                });
    }


    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
