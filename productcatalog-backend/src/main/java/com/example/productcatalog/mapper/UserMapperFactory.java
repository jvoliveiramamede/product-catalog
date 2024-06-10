package com.example.productcatalog.mapper;

import org.modelmapper.ModelMapper;

import com.example.productcatalog.dto.UserCreateRequest;
import com.example.productcatalog.dto.UserResponse;
import com.example.productcatalog.model.User;

public class UserMapperFactory {
    
    private static final ModelMapper modelMapper = new ModelMapper();

    public static User convertCreateToEntity(UserCreateRequest request) {
        try {
            return modelMapper.map(request, User.class);
        } catch (Exception e) {
            throw new MapperException("Erro to convert request create user to entity user", e);
        }
    }

    public static UserResponse convertToResponse(User entity) throws MapperException {
        try {
            return modelMapper.map(entity, UserResponse.class);
        } catch (Exception e) {
            throw new MapperException("Erro to convert entity to response user", e);
        }
    }

}
