package com.example.productcatalog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.productcatalog.dto.ProductCreateRequest;
import com.example.productcatalog.dto.ProductResponse;
import com.example.productcatalog.exception.ProductInternalErrorException;
import com.example.productcatalog.model.Product;
import com.example.productcatalog.repository.ProductRepository;

public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach()
    private void setUp() {
        MockitoAnnotations.openMocks(this);
        this.service = new ProductServiceImpl(repository);
    }

    @Test
    public void testSaveProduct_Success() {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .title("Valid Title")
            .name("Valid Name")
            .description("Valid Description")
            .price(100.0)
            .build();
        
        Product product = new Product();
        product.setId("abc123");
        product.setTitle(request.getTitle());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        when(repository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = service.save(request);

        assertEquals(response.getName(), request.getName());
    }


    @Test
    public void testSaveProduct_InternalError() {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .title("Valid Title")
            .name("Valid Name")
            .description("Valid Description")
            .price(100.0)
            .build();
        
        // Mock repository to throw ProductInternalErrorException
        when(repository.save(any(Product.class))).thenThrow(new RuntimeException("Simulated repository error"));

        // Perform the test and verify exception handling
        try {
            service.save(request);
        } catch (ProductInternalErrorException e) {
            assertEquals("An error occurred while saving the product", e.getMessage());
            // Optionally, assert more details about the exception if needed
            return; // Test passed if exception was thrown
        }

        // If we reach here, the exception was not thrown as expected
        throw new AssertionError("Expected ProductInternalErrorException was not thrown");
    }
}