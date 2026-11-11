package com.example.Product;


import com.example.data.InMemoryStore;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;

import java.util.ArrayList;
import java.util.List;

@Controller("/products")
public class ProductsController {

    private final InMemoryStore store;

    public ProductsController(InMemoryStore store) {
        this.store = store;
    }


    @Get
    public List<Product> getAllProducts()
    {
        return new ArrayList<>(store.getProducts().values()) ;
    }

    @Get("{id}")
    public Product getproduct(@PathVariable Integer id)
    {
        return store.getProducts().get(id);
    }

    @Status(HttpStatus.CREATED)
    @Post("/add")
    public Product addProduct(@Body Product product) {
        return store.addProduct(product);
    }

    @Delete("{id}")
    public void Deleteid(@PathVariable Integer id)
    {
        if(store.getProducts().get(id)!=null)
        {
            store.deleteProduct(id);
        }else{
            throw new HttpStatusException(
                    HttpStatus.NOT_FOUND,
                    "Product with id " + id + " not found"
            );
        }

    }
}
