package com.example.productcatalog.exception;

public class ProductInternalErrorException extends RuntimeException {
    public ProductInternalErrorException(String message) {
        super(message);
    }

    public ProductInternalErrorException(String message, Throwable e) {
        super(message, e);
    }
}
