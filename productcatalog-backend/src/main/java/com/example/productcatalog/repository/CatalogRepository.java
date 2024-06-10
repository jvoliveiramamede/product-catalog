package com.example.productcatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.productcatalog.model.Catalog;

public interface CatalogRepository extends MongoRepository<Catalog, String> {}