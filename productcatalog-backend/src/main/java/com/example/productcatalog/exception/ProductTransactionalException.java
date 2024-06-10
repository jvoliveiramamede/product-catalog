package com.example.productcatalog.exception;

public class ProductTransactionalException extends RuntimeException {
    
    public ProductTransactionalException(String message) {
        super(message);
    }

    public ProductTransactionalException(String message, Throwable e) {
        super(message, e);
    }

}
