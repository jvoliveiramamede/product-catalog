package com.example.productcatalog.service;

import org.springframework.stereotype.Service;

import com.example.productcatalog.dto.CatalogCreateRequest;
import com.example.productcatalog.dto.CatalogResponse;
import com.example.productcatalog.mapper.CatalogMapperFactory;
import com.example.productcatalog.model.Catalog;
import com.example.productcatalog.repository.CatalogRepository;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository repository;

    public CatalogServiceImpl(CatalogRepository catalogRepository) {
        this.repository = catalogRepository;
    }

    @Override
    public CatalogResponse create(CatalogCreateRequest request) {
        Catalog entity = this.repository.save(CatalogMapperFactory.convertCreateToEntity(request));
        return CatalogMapperFactory.convertToResponse(entity);
    }
    
}
