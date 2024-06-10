package com.example.productcatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.productcatalog.model.Product;
import java.util.List;
import java.util.Optional;


public interface ProductRepository extends MongoRepository<Product, String> {
    
    Optional<List<Product>> findByName(String name);

    Optional<Product> getByName(String name);
    long count();
}
