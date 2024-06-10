package com.example.productcatalog.service;

import com.example.productcatalog.dto.UserCreateRequest;
import com.example.productcatalog.dto.UserResponse;

public interface UserService {
    
    public UserResponse create(UserCreateRequest request);
    public UserResponse byId(String id);
    public UserResponse byUsername(String username);

}
