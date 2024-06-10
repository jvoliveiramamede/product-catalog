package com.example.productcatalog.mapper;

public class MapperException extends RuntimeException {
    
    public MapperException(String message) {
        super(message);
    }

    public MapperException(String message, Throwable e) {
        super(message, e);
    }

}
