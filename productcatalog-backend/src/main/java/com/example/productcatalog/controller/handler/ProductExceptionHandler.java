package com.example.productcatalog.controller.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.productcatalog.exception.ProductInternalErrorException;
import com.example.productcatalog.exception.ProductNotFoundException;
import com.example.productcatalog.exception.ProductTransactionalException;

@RestControllerAdvice
public class ProductExceptionHandler {
    
    @ExceptionHandler(ProductInternalErrorException.class)
    public ResponseEntity<ErrorResponse> handlerInternalError(ProductInternalErrorException exception) {
        int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        Map<String, String> errors = new HashMap<>();
        errors.put("error", exception.getMessage());

        ErrorResponse response = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .errors(errors)
            .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
            .code(code)
            .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ProductTransactionalException.class)
    public ResponseEntity<ErrorResponse> handlerTransactionError(ProductTransactionalException exception) {
        int code = HttpStatus.BAD_REQUEST.value();

        Map<String, String> errors = new HashMap<>();
        errors.put("error", exception.getMessage());

        ErrorResponse response = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .errors(errors)
            .status(HttpStatus.BAD_GATEWAY.toString())
            .code(code)
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerNotFoundException(ProductNotFoundException exception) {
        int code = HttpStatus.NOT_FOUND.value();

        Map<String, String> errors = new HashMap<>();
        errors.put("error", exception.getMessage());

        ErrorResponse response = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .errors(errors)
            .status(HttpStatus.NOT_FOUND.toString())
            .code(code)
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
