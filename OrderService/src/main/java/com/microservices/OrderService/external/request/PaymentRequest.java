package com.microservices.OrderService.external.request;

import com.microservices.OrderService.model.PaymentMode;
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
