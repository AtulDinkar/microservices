package com.microservices.PaymentService.service.impl;

import com.microservices.PaymentService.entity.TransactionDetails;
import com.microservices.PaymentService.model.PaymentMode;
import com.microservices.PaymentService.model.PaymentRequest;
import com.microservices.PaymentService.model.PaymentResponse;
import com.microservices.PaymentService.repository.TransactionDetailsRepository;
import com.microservices.PaymentService.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording payment request :: {} " + paymentRequest);

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentMode(PaymentMode.CASH)
                .paymentDate(Instant.now())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .totalAmount(paymentRequest.getAmount())
                .build();

        transactionDetailsRepository.save(transactionDetails);

        log.info("Payment completed for id :: {} ", transactionDetails.getId());
        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getorderByOrderId(String orderId) {

        log.info("getting payment info for order id:: {}", orderId);
        TransactionDetails byOrderId = transactionDetailsRepository.findByOrderId(Long.parseLong(orderId));
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(byOrderId.getId())
                .paymentMode(PaymentMode.CASH)
                .paymentDate(byOrderId.getPaymentDate())
                .orderId(byOrderId.getOrderId())
                .paymentStatus(byOrderId.getPaymentStatus())
                .totalAmount(byOrderId.getTotalAmount())
                .build();
        return paymentResponse;
    }
}
