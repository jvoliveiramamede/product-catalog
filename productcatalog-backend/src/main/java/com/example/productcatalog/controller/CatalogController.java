package com.example.productcatalog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.productcatalog.dto.CatalogCreateRequest;
import com.example.productcatalog.dto.CatalogResponse;
import com.example.productcatalog.service.CatalogService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "api/catalog")
public class CatalogController {
    
    private final CatalogService service;

    public CatalogController(CatalogService catalogService) {
        this.service = catalogService;
    }

    @PostMapping("create")
    public ResponseEntity<CatalogResponse> create(@RequestBody CatalogCreateRequest request) {
       CatalogResponse response = this.service.create(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    

}
