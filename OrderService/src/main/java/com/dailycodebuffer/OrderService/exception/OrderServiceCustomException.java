package com.dailycodebuffer.OrderService.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderServiceCustomException extends RuntimeException{
    private String errorCode;
    private Integer status;

    public OrderServiceCustomException(String message, String errorCode, Integer status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
