package com.example.productcatalog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.productcatalog.dto.ProductCreateRequest;
import com.example.productcatalog.dto.ProductListResponse;
import com.example.productcatalog.dto.ProductResponse;
import com.example.productcatalog.dto.ProductUpdateRequest;
import com.example.productcatalog.exception.ProductInternalErrorException;
import com.example.productcatalog.exception.ProductNotFoundException;
import com.example.productcatalog.service.ProductService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(value = "api/product")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService productService) {
        this.service = productService;
    }
    
    @PostMapping(value = "create")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductCreateRequest request) {
        ProductResponse response = this.service.save(request);
        response.add(
            WebMvcLinkBuilder.linkTo(ProductController.class).slash("list/").withRel("list"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getById(response.getId())).withSelfRel()
        );
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("list")
    public ResponseEntity<ProductListResponse> list(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        ProductListResponse productListResponse = this.service.list(page, pageSize);

        productListResponse.add(WebMvcLinkBuilder.linkTo(ProductController.class).slash("list").withSelfRel());

        List<ProductResponse> productsWithLinks = productListResponse.getProducts().stream()
            .map(product -> {
                product.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getById(product.getId())).withSelfRel());
                product.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getByName(product.getName())).withSelfRel());
                product.add(WebMvcLinkBuilder.linkTo(ProductController.class).slash("create/").withRel("create"));
                product.add(WebMvcLinkBuilder.linkTo(ProductController.class).slash("update/" + product.getId()).withRel("update"));
                product.add(WebMvcLinkBuilder.linkTo(ProductController.class).slash("updatePartial/" + product.getId()).withRel("updatePartial"));
                return product;
            })
            .collect(Collectors.toList());

        productListResponse.setProducts(productsWithLinks);

        return ResponseEntity.status(HttpStatus.OK).body(productListResponse);
    }

    @GetMapping("get/byName/{name}")
    public ResponseEntity<ProductResponse> getByName(@PathVariable("name") String name) {
        ProductResponse response = this.service.byName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("get/byId/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable("id") String id) {
        ProductResponse response = this.service.byId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    
    @PutMapping("update/byId/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable("id") String id, @RequestBody ProductUpdateRequest request) {
        if (id ==  null) {
            throw new ProductInternalErrorException("Id is required in path");
        }
        request.setId(id);
        ProductResponse response = this.service.updateById(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/updatePartial/byId/{id}")
    public ResponseEntity<ProductResponse> updatePartial(@PathVariable("id") String id, @RequestBody ProductUpdateRequest request) {
        if (id == null) {
            throw new ProductInternalErrorException("ID is rquired in the path.");
        }
        request.setId(id);
        ProductResponse response = this.service.updatePartialById(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/byId/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id) {
        if (id ==  null) {
            throw new ProductInternalErrorException("Id is required in path");
        }

        Boolean response = this.service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}