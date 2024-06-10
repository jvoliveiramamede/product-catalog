package com.example.productcatalog.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents a DTO for creating or updating a product.
 * <p>
 * This DTO is used to transfer data between the client and the server
 * for operations related to products, such as creation or update.
 * </p>
 * 
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequest implements Serializable {
    
    /**
     * The required title of the product.
     */
    @NotNull(message = "Title is required")
    private String title;
    
    /**
     * The required name of the product.
     */
    @NotNull(message = "Name is required")
    private String name;
    
    /**
     * The description of the product.
     */
    private String description;
    
    /**
     * The price of the product.
     */
    private Double price;
}
