package com.example.productcatalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a product entity in the catalog.
 * <p>
 * This class defines the structure and attributes of a product entity stored in MongoDB.
 * Each product has a unique ID and title. The title and name fields are indexed for efficient querying.
 * </p>
 * 
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product")
public class Product {
    
    /**
     * The unique identifier for the product.
     */
    @Id
    private String id;
    
    /**
     * The title of the product.
     */
    @Indexed(unique = true, name = "title")
    private String title;
    
    /**
     * The name of the product.
     */
    @Indexed(unique = true)
    private String name;
    
    /**
     * A brief description of the product.
     */
    private String description;

    /**
     * The price of the product.
     */
    private Double price;
    /**
     * The catalog about this product.
     * @see Catalog
     */
    @DocumentReference(lazy = true)
    private Catalog catalog;
}
