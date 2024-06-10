package com.example.productcatalog.service;

import com.example.productcatalog.dto.CatalogCreateRequest;
import com.example.productcatalog.dto.CatalogResponse;

public interface CatalogService {
    public CatalogResponse create(CatalogCreateRequest request);
}
