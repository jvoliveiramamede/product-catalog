package com.example.productcatalog.controller.handler;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private Map<String, String> errors;
    private LocalDateTime timestamp;
    private String status;
    private int code;
}
