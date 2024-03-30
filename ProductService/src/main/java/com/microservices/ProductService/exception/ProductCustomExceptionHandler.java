package com.microservices.ProductService.exception;

import lombok.Data;

@Data
public class ProductCustomExceptionHandler extends RuntimeException {
    private String errorCode;

    public ProductCustomExceptionHandler(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
