package com.example.productcatalog.mapper;

import org.modelmapper.ModelMapper;

import com.example.productcatalog.dto.ProductCreateRequest;
import com.example.productcatalog.dto.ProductResponse;
import com.example.productcatalog.model.Product;

public class ProductMapperFactory {

    private static final ModelMapper modelMapper = new ModelMapper();
    
    public static Product convertToEntity(ProductCreateRequest request) throws MapperException {
        try {
            return modelMapper.map(request, Product.class);
        } catch (Exception e) {
            throw new MapperException("Erro to convert ProductRequest class to Product entity");
        }
    }

    public static ProductResponse convertToResponse(Product entity) throws MapperException {
        try {
            return modelMapper.map(entity, ProductResponse.class);
        } catch (Exception e) {
           throw new MapperException("Error to convert Product entity to ProductResponse class");
        }
    }

}
