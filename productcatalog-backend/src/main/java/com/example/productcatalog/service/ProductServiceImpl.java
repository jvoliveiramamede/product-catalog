package com.example.productcatalog.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import com.example.productcatalog.dto.ProductCreateRequest;
import com.example.productcatalog.dto.ProductListResponse;
import com.example.productcatalog.dto.ProductResponse;
import com.example.productcatalog.dto.ProductUpdateRequest;
import com.example.productcatalog.exception.ProductInternalErrorException;
import com.example.productcatalog.exception.ProductNotFoundException;
import com.example.productcatalog.exception.ProductTransactionalException;
import com.example.productcatalog.mapper.ProductMapperFactory;
import com.example.productcatalog.model.Product;
import com.example.productcatalog.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public ProductResponse save(ProductCreateRequest request) throws ProductTransactionalException, ProductInternalErrorException {
        try {
            
            Product entity = ProductMapperFactory.convertToEntity(request);
            Product savedProduct = this.repository.save(entity);
            ProductResponse response = ProductMapperFactory.convertToResponse(savedProduct);

            return response;
        } catch (TransactionSystemException e) {
            throw new ProductTransactionalException("Transaction error with database", e);
        } catch (Exception e) {
            throw new ProductInternalErrorException("An error occurred while saving the product", e);
        }
    }

    @Override
    public ProductResponse byId(String id) throws ProductNotFoundException, ProductInternalErrorException {
        try {
            Optional<Product> entity = Optional.ofNullable(this.repository.findById(id))
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

            if (entity.isPresent()) {
                return ProductMapperFactory.convertToResponse(entity.get());
            } else {
                throw new ProductNotFoundException("Product not found with ID: " + id);
            }
            
        } catch (ProductNotFoundException exception) {
            throw exception;
        } catch (Exception e) {
            throw new ProductInternalErrorException("An error occured while get by id the product", e);
        }
    }

    @Override
    public List<ProductResponse> byName(String name) throws ProductNotFoundException, ProductInternalErrorException {
        try {
            List<Product> products = this.repository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with Name: " + name));

            
            if (!products.isEmpty()) {
                return products.stream().map(
                    product -> ProductMapperFactory.convertToResponse(product)
                ).collect(Collectors.toList());
            } else {
                throw new ProductNotFoundException("Product not found with Name: " + name);
            }

        } catch (ProductNotFoundException exception) {
            throw exception;
        } catch (Exception e) {
            throw new ProductInternalErrorException("An error occured while get by name the product", e);
        }
    }

    @Override
    public ProductResponse updateById(ProductUpdateRequest request)
            throws ProductNotFoundException, ProductTransactionalException, ProductInternalErrorException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateById'");
    }

    @Override
    public ProductResponse updatePartialById(ProductUpdateRequest request)
            throws ProductNotFoundException, ProductTransactionalException, ProductInternalErrorException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePartialById'");
    }

    @Override
    public ProductListResponse list() throws ProductInternalErrorException {
        try {
            List<Product> products = this.repository.findAll();
            List<ProductResponse> response = products.stream()
                .map(ProductMapperFactory::convertToResponse)
                .collect(Collectors.toList());
            
            long total = this.repository.count();

            return ProductListResponse.builder()
                .products(response)
                .total(total)
                .build();
            
        } catch (Exception e) {
            throw new ProductInternalErrorException("An error occured while list in product", e);
        }
    }


}
