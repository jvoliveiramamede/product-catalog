package com.example.productcatalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "category")
public class Category {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    @Indexed(unique = true)
    private String title;
}
