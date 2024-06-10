package com.example.productcatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.productcatalog.model.User;

public interface UserRepository extends MongoRepository<User, String> {}