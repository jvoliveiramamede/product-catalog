package com.example.productcatalog.dto;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListResponse extends RepresentationModel<ProductListResponse> {
    private List<ProductResponse> products;
    private long total;
}
