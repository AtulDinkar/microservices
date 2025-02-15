package com.microservices.PaymentService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    private long id;
    private long amount;
    private long orderId;
    private String referenceNumber;
    private PaymentMode paymentMode;
}
