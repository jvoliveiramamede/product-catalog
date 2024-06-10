package com.example.productcatalog.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogUpdateRequest implements Serializable {
    private String id;
    private String name;
    private String category;
}