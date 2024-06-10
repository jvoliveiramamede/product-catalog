package com.example.productcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

/**
 * Represents a DTO for responding with product details.
 * <p>
 * This DTO is used to transfer product data from the server to the client in response to queries or requests.
 * </p>
 * 
 * @version 1.0
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends RepresentationModel<ProductResponse> implements Serializable {
    
    /**
     * The unique identifier of the product.
     */
    private String id;
    
    /**
     * The title of the product.
     */
    private String title;
    
    /**
     * The name of the product.
     */
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
