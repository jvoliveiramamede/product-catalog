package com.example.productcatalog.service;

import com.example.productcatalog.dto.ProductCreateRequest;
import com.example.productcatalog.dto.ProductListResponse;
import com.example.productcatalog.dto.ProductResponse;
import com.example.productcatalog.dto.ProductUpdateRequest;
import com.example.productcatalog.exception.ProductInternalErrorException;
import com.example.productcatalog.exception.ProductNotFoundException;
import com.example.productcatalog.exception.ProductTransactionalException;

/**
 * Service interface for managing products in the product catalog.
 */
public interface ProductService {

    /**
     * Saves a new product to the database.
     *
     * @param request DTO containing the product information to be saved.
     * @return the saved product.
     * @throws ProductTransactionalException if there is a transaction error with the database.
     * @throws ProductInternalErrorException if there is a general server error.
     */
    ProductResponse save(ProductCreateRequest request) 
        throws ProductTransactionalException, ProductInternalErrorException;

    /**
     * Retrieves a product by its ID from the database.
     *
     * @param id the unique identifier of the product.
     * @return the product with the specified ID.
     * @throws ProductNotFoundException if the product with the given ID is not found.
     * @throws ProductInternalErrorException if there is a general server error.
     */
    ProductResponse byId(String id) 
        throws ProductNotFoundException, ProductInternalErrorException;


    /**
     * Updates an existing product by its ID in the database.
     *
     * @param request DTO containing the updated product information.
     * @return the updated product.
     * @throws ProductNotFoundException if the product with the given ID is not found.
     * @throws ProductTransactionalException if there is a transaction error with the database.
     * @throws ProductInternalErrorException if there is a general server error.
     */
    ProductResponse updateById(ProductUpdateRequest request) 
        throws ProductNotFoundException, ProductTransactionalException, ProductInternalErrorException;

    /**
     * Partially updates an existing product by its ID in the database.
     *
     * @param request DTO containing the partial updated product information.
     * @return the partially updated product.
     * @throws ProductNotFoundException if the product with the given ID is not found.
     * @throws ProductTransactionalException if there is a transaction error with the database.
     * @throws ProductInternalErrorException if there is a general server error.
     */
    ProductResponse updatePartialById(ProductUpdateRequest request) 
        throws ProductNotFoundException, ProductTransactionalException, ProductInternalErrorException;

    /**
     * Retrieves a list of all products from the database.
     * @param page the page number (starting from 0)
     * @param pageSize the size of each page.
     * @return a pagination list of products.
     * @throws ProductInternalErrorException if there is a general server error.
     */
    ProductListResponse list(int page, int pageSize) throws ProductInternalErrorException;

    /**
     * Retrieves a list of products by their name from the database.
     *
     * @param name the name of the products to search for.
     * @return a list of products with the specified name.
     * @throws ProductNotFoundException if no products with the given name are found.
     * @throws ProductInternalErrorException if there is a general server error.
     */
    ProductResponse byName(String name) 
        throws ProductNotFoundException, ProductInternalErrorException;

    /**
     * 
     * @param id
     * @return
     * @throws ProductNotFoundException
     * @throws ProductTransactionalException
     * @throws ProductInternalErrorException
     */
    Boolean deleteById(String id)
        throws ProductNotFoundException, ProductTransactionalException, ProductInternalErrorException;
}
