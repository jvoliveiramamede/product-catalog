package com.example.productcatalog.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.productcatalog.dto.CatalogCreateRequest;
import com.example.productcatalog.dto.CatalogResponse;
import com.example.productcatalog.model.Catalog;

public class CatalogMapperFactory {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Catalog convertCreateToEntity(CatalogCreateRequest request) {
        try {
            return modelMapper.map(request, Catalog.class);
        } catch (Exception e) {
            throw new MapperException("Erro to convert request create catalog to entity catalog", e);
        }
    }

    public static CatalogResponse convertToResponse(Catalog entity) {
        try {
            return modelMapper.map(entity, CatalogResponse.class);
        } catch (Exception e) {
            throw new MapperException("Erro to convert request create catalog to entity catalog", e);
        }
    }
}
