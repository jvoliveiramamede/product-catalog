package com.example.productcatalog.service;

import org.springframework.stereotype.Service;

import com.example.productcatalog.dto.UserCreateRequest;
import com.example.productcatalog.dto.UserResponse;
import com.example.productcatalog.mapper.UserMapperFactory;
import com.example.productcatalog.model.User;
import com.example.productcatalog.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public UserResponse create(UserCreateRequest request) {
        User entity = UserMapperFactory.convertCreateToEntity(request);
        UserResponse response = UserMapperFactory.convertToResponse(this.repository.save(entity));

        return response;
    }

    @Override
    public UserResponse byId(String id) {
        User entity = this.repository.findById(id).get();

        return UserMapperFactory.convertToResponse(entity);
    }

    @Override
    public UserResponse byUsername(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'byUsername'");
    }
    


}
