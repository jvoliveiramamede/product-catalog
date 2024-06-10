package com.example.productcatalog.service;

import java.util.List;

import com.example.productcatalog.dto.ProductCreateRequest;
import com.example.productcatalog.dto.ProductListResponse;
import com.example.productcatalog.dto.ProductResponse;
import com.example.productcatalog.dto.ProductUpdateRequest;
import com.example.productcatalog.exception.ProductInternalErrorException;
import com.example.productcatalog.exception.ProductNotFoundException;
import com.example.productcatalog.exception.ProductTransactionalException;

public interface ProductService {

    ProductResponse save(ProductCreateRequest request) throws ProductTransactionalException, ProductInternalErrorException;
    ProductResponse byId(String id) throws ProductNotFoundException, ProductInternalErrorException;
    List<ProductResponse> byName(String name) throws ProductNotFoundException, ProductInternalErrorException;
    ProductResponse updateById(ProductUpdateRequest request) throws ProductNotFoundException, ProductTransactionalException, ProductInternalErrorException;
    ProductResponse updatePartialById(ProductUpdateRequest request) throws ProductNotFoundException, ProductTransactionalException, ProductInternalErrorException;
    ProductListResponse list() throws ProductInternalErrorException;
}
