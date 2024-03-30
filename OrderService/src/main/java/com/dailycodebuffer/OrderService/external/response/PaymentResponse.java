package com.dailycodebuffer.OrderService.external.response;

import com.dailycodebuffer.OrderService.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private long paymentId;
    private String paymentStatus;
    private long orderId;
    private PaymentMode paymentMode;
    private Instant paymentDate;
    private long totalAmount;
}
