package com.example.productcatalog.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductUpdateRequest {
    private String id;
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
