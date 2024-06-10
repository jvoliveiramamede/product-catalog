package com.example.productcatalog.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

/**
 * Implementation of the ProductService interface for managing products.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves a new product to the database.
     *
     * @param request DTO containing the product information to be saved.
     * @return the saved product.
     * @throws ProductTransactionalException if there is a transaction error with the database.
     * @throws ProductInternalErrorException if there is a general server error.
     */
    @Override
    @Transactional
    public ProductResponse save(ProductCreateRequest request) 
            throws ProductTransactionalException, ProductInternalErrorException {
        try {
            Product entity = ProductMapperFactory.convertToEntity(request);
            Product savedProduct = this.repository.save(entity);
            return ProductMapperFactory.convertToResponse(savedProduct);
        } catch (TransactionSystemException e) {
            throw new ProductTransactionalException("Transaction error with database", e);
        } catch (Exception e) {
            throw new ProductInternalErrorException("An error occurred while saving the product", e);
        }
    }

    /**
     * Retrieves a product by its ID from the database.
     *
     * @param id the unique identifier of the product.
     * @return the product with the specified ID.
     * @throws ProductNotFoundException if the product with the given ID is not found.
     * @throws ProductInternalErrorException if there is a general server error.
     */
    @Override
    public ProductResponse byId(String id) 
            throws ProductNotFoundException, ProductInternalErrorException {
        try {
            Product entity = this.repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
            return ProductMapperFactory.convertToResponse(entity);
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductInternalErrorException("An error occurred while getting the product by ID", e);
        }
    }

    /**
     * Updates an existing product by its ID in the database.
     *
     * @param request DTO containing the updated product information.
     * @return the updated product.
     * @throws ProductNotFoundException if the product with the given ID is not found.
     * @throws ProductTransactionalException if there is a transaction error with the database.
     * @throws ProductInternalErrorException if there is a general server error.
     */
    @Override
    @Transactional
    public ProductResponse updateById(ProductUpdateRequest request) 
            throws ProductNotFoundException, ProductTransactionalException, ProductInternalErrorException {
        try {
            Product existingProduct = this.repository.findById(request.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + request.getId()));
            existingProduct.setName(request.getName());
            existingProduct.setTitle(request.getTitle());
            existingProduct.setDescription(request.getDescription());
            existingProduct.setPrice(request.getPrice());
            Product savedProduct = this.repository.save(existingProduct);
            return ProductMapperFactory.convertToResponse(savedProduct);
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (TransactionSystemException e) {
            throw new ProductTransactionalException("Transaction error with database", e);
        } catch (Exception e) {
            throw new ProductInternalErrorException("An error occurred while updating the product", e);
        }
    }

    /**
     * Partially updates an existing product by its ID in the database.
     *
     * @param request DTO containing the partial updated product information.
     * @return the partially updated product.
     * @throws ProductNotFoundException if the product with the given ID is not found.
     * @throws ProductTransactionalException if there is a transaction error with the database.
     * @throws ProductInternalErrorException if there is a general server error.
     */
    @Override
    @Transactional
    public ProductResponse updatePartialById(ProductUpdateRequest request) 
            throws ProductNotFoundException, ProductTransactionalException, ProductInternalErrorException {
        try {
            Product existingProduct = this.repository.findById(request.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + request.getId()));
            if (request.getName() != null) {
                existingProduct.setName(request.getName());
            }
            if(request.getTitle() != null) {
                existingProduct.setTitle(request.getTitle());
            }
            if (request.getDescription() != null) {
                existingProduct.setDescription(request.getDescription());
            }
            if (request.getPrice() != null) {
                existingProduct.setPrice(request.getPrice());
            }
            Product savedProduct = this.repository.save(existingProduct);
            return ProductMapperFactory.convertToResponse(savedProduct);
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (TransactionSystemException e) {
            throw new ProductTransactionalException("Transaction error with database", e);
        } catch (Exception e) {
            throw new ProductInternalErrorException("An error occurred while partially updating the product", e);
        }
    }

    /**
     * Retrieves a list of all products from the database.
     * @param page the page number (starting from 0)
     * @param pageSize the size of each page.
     * @return a pagination list of products.
     * @return a list of all products.
     * @throws ProductInternalErrorException if there is a general server error.
     */
    @Override
    public ProductListResponse list(int page, int pageSize) 
            throws ProductInternalErrorException {
        try {
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<Product> productPage = repository.findAll(pageable);

            List<ProductResponse> response = productPage.stream()
                .map(ProductMapperFactory::convertToResponse)
                .collect(Collectors.toList());
            long total = this.repository.count();
            return ProductListResponse.builder()
                .products(response)
                .total(total)
                .build();
        } catch (Exception e) {
            throw new ProductInternalErrorException("An error occurred while listing the products", e);
        }
    }

    /**
     * Retrieves a product by its Name from the database.
     *
     * @param name the unique identifier of the product.
     * @return the product with the specified Name.
     * @throws ProductNotFoundException if the product with the given name is not found.
     * @throws ProductInternalErrorException if there is a general server error.
     */
    @Override
    public ProductResponse byName(String name) throws ProductNotFoundException, ProductInternalErrorException {
        try {
            Product existedProduct = (this.repository.getByName(name))
                .orElseThrow(() -> new ProductNotFoundException("Product not found with this name: " + name));

            return ProductMapperFactory.convertToResponse(existedProduct);
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductInternalErrorException("An error occurred while get by name the product", e);
        }
    }

    /**
     * 
     * @param id
     * @return
     * @throws ProductNotFoundException
     * @throws ProductTransactionalException
     * @throws ProductInternalErrorException
     */
    @Override
    @Transactional
    public Boolean deleteById(String id)
            throws ProductNotFoundException, ProductTransactionalException, ProductInternalErrorException {
        try {
            Product existedProduct = (this.repository.findById(id))
                .orElseThrow(() -> new ProductNotFoundException("Product not found with this ID: " + id));
            repository.delete(existedProduct);
            return true;
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (TransactionSystemException e) {
            throw new ProductTransactionalException("Transaction error with database", e);
        } catch (Exception e) {
            throw new ProductInternalErrorException("An error occurred while get by name the product", e);
        }
    }
}
