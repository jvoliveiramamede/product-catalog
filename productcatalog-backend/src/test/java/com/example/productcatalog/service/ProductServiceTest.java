package com.example.productcatalog.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.TransactionSystemException;

import com.example.productcatalog.dto.ProductCreateRequest;
import com.example.productcatalog.dto.ProductResponse;
import com.example.productcatalog.dto.ProductUpdateRequest;
import com.example.productcatalog.exception.ProductInternalErrorException;
import com.example.productcatalog.exception.ProductNotFoundException;
import com.example.productcatalog.exception.ProductTransactionalException;
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
    public void testSaveProduct_TransactionError() {
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
        when(repository.save(any(Product.class)))
                .thenThrow(new TransactionSystemException("Transaction error with database"));
        try {
            service.save(request);
        } catch (ProductTransactionalException e) {
            assertEquals("Transaction error with database", e.getMessage());
            return;
        }
        throw new AssertionError("Expected ProductTransactionalException was not thrown");
    }

    @Test
    public void testSaveProduct_InternalError() {
        ProductCreateRequest request = ProductCreateRequest.builder()
                .title("Valid Title")
                .name("Valid Name")
                .description("Valid Description")
                .price(100.0)
                .build();
        when(repository.save(any(Product.class))).thenThrow(new RuntimeException("Simulated repository error"));
        try {
            service.save(request);
        } catch (ProductInternalErrorException e) {
            assertEquals("An error occurred while saving the product", e.getMessage());
            return;
        }
        throw new AssertionError("Expected ProductInternalErrorException was not thrown");
    }

    @Test
    public void testGetProductById_Success() {
        String productId = "abcnotexisting";
        Product product = new Product();
        product.setTitle("Testing");
        product.setName("Testing");
        product.setDescription("Valid Description");
        product.setPrice(100.0);
        when(repository.findById(productId)).thenReturn(Optional.of(product));
        ProductResponse respone = service.byId(productId);
        assertEquals(respone.getName(), product.getName());
    }

    @Test
    public void testGetProductById_NotFoundError() {
        String productId = "abcnotexisting";
        when(repository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> {
            service.byId(productId);
        });
    }

    @Test
    public void testGetProductById_InternalError() {
        String productId = "abcnotexisting";
        when(repository.findById(productId)).thenThrow(new RuntimeException("Simulated repository error"));
        assertThrows(ProductInternalErrorException.class, () -> {
            service.byId(productId);
        });
    }

    @Test
    public void testGetProductByName_Success() {
        Product product = new Product();
        product.setTitle("Testing");
        product.setName("Testing");
        product.setDescription("Valid Description");
        product.setPrice(100.0);
        when(repository.getByName(product.getName())).thenReturn(Optional.of(product));
        ProductResponse respone = service.byName(product.getName());
        assertEquals(respone.getName(), product.getName());
    }

    @Test
    public void testGetProductByName_NotFoundError() {
        String name = "Testing";
        when(repository.getByName(name)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> {
            service.byName(name);
        });
    }

    @Test
    public void testGetProductByName_InternalError() {
        String name = "Testing";
        when(repository.getByName(name)).thenThrow(new RuntimeException("Simulated repository error"));
        assertThrows(ProductInternalErrorException.class, () -> {
            service.byName(name);
        });
    }

    @Test
    public void testUpdateProductById_Success() {
        String productId = "abctesting";
        ProductUpdateRequest request = (ProductUpdateRequest) ProductUpdateRequest.builder()
                .id(productId)
                .name("Updated Name")
                .description("Updated Description")
                .price(200.0)
                .build();
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Name");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(100.0);
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName(request.getName());
        updatedProduct.setDescription(request.getDescription());
        updatedProduct.setPrice(request.getPrice());

        when(repository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(repository.save(existingProduct)).thenReturn(updatedProduct);

        ProductResponse response = service.updateById(request);

        assertEquals(response.getId(), updatedProduct.getId());
        assertEquals(response.getName(), updatedProduct.getName());
        assertEquals(response.getDescription(), updatedProduct.getDescription());
        assertEquals(response.getPrice(), updatedProduct.getPrice());
    }

    @Test
    public void testUpdateProductById_NotFoundError() {
        String productId = "abcnotexist";
        ProductUpdateRequest request = ProductUpdateRequest.builder()
                .id(productId)
                .name("Updated Name")
                .description("Updated Description")
                .price(200.0)
                .build();
        when(repository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            service.updateById(request);
        });
    }

    @Test
    public void testUpdateProductById_TransactionError() {
        String productId = "abcnotexist";
        ProductUpdateRequest request = ProductUpdateRequest.builder()
                .id(productId)
                .name("Updated Name")
                .description("Updated Description")
                .price(200.0)
                .build();
        when(repository.findById(productId))
                .thenThrow(new TransactionSystemException("Transaction error with database"));
        try {
            service.updateById(request);
        } catch (ProductTransactionalException e) {
            assertEquals("Transaction error with database", e.getMessage());
            return;
        }
        throw new AssertionError("Expected ProductTransactionalException was not thrown");
    }

    @Test
    public void testUpdateProductById_InternalError() {
        String productId = "abcnotexist";
        ProductUpdateRequest request = ProductUpdateRequest.builder()
                .id(productId)
                .name("Updated Name")
                .description("Updated Description")
                .price(200.0)
                .build();
        when(repository.findById(productId)).thenThrow(new RuntimeException("Simulated repository error"));
        try {
            service.updateById(request);
        } catch (ProductInternalErrorException e) {
            assertEquals("An error occurred while updating the product", e.getMessage());
            return;
        }
        throw new AssertionError("Expected ProductInternalErrorException was not thrown");
    }

    @Test
    public void testUpdatePartialProductById_Success() {
        String productId = "abctesting";
        ProductUpdateRequest request = ProductUpdateRequest.builder()
                .id(productId)
                .name("Updated Name")
                .description("Updated Description")
                .price(200.0)
                .build();
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Name");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(100.0);
        Product partiallyUpdatedProduct = new Product();
        partiallyUpdatedProduct.setId(productId);
        partiallyUpdatedProduct.setName(request.getName());
        partiallyUpdatedProduct.setDescription("Old Description");
        partiallyUpdatedProduct.setPrice(100.0);

        when(repository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(repository.save(existingProduct)).thenReturn(partiallyUpdatedProduct);

        ProductResponse response = service.updatePartialById(request);

        assertEquals(response.getId(), partiallyUpdatedProduct.getId());
        assertEquals(response.getName(), partiallyUpdatedProduct.getName());
        assertEquals(response.getDescription(), partiallyUpdatedProduct.getDescription());
        assertEquals(response.getPrice(), partiallyUpdatedProduct.getPrice());
    }

    @Test
    public void testUpdatePartialProductById_ProductNotFoundError() {
        String productId = "nonExistingId";
        ProductUpdateRequest request = ProductUpdateRequest.builder()
            .id(productId)
            .name("Partially Updated Name")
            .build();
        
        when(repository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            service.updatePartialById(request);
        });
    }

    @Test
    public void testUpdatePartialProductById_TransactionError() {
        String productId = "abc123";
        ProductUpdateRequest request = ProductUpdateRequest.builder()
            .id(productId)
            .name("Partially Updated Name")
            .build();
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Name");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(100.0);
        
        when(repository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(repository.save(existingProduct)).thenThrow(new TransactionSystemException("Transaction error with database"));

        ProductTransactionalException exception = assertThrows(ProductTransactionalException.class, () -> {
            service.updatePartialById(request);
        });

        assertEquals("Transaction error with database", exception.getMessage());
    }

    @Test
    public void testUpdatePartialProductById_InternalError() {
        String productId = "abc123";
        ProductUpdateRequest request = ProductUpdateRequest.builder()
            .id(productId)
            .name("Partially Updated Name")
            .build();
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Name");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(100.0);
        
        when(repository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(repository.save(existingProduct)).thenThrow(new RuntimeException("Simulated repository error"));

        ProductInternalErrorException exception = assertThrows(ProductInternalErrorException.class, () -> {
            service.updatePartialById(request);
        });

        assertEquals("An error occurred while partially updating the product", exception.getMessage());
    }

    @Test
    public void testListProduct_Success() {
        
    }

}