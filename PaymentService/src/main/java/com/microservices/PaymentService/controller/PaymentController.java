package com.microservices.PaymentService.controller;

import com.microservices.PaymentService.model.PaymentRequest;
import com.microservices.PaymentService.model.PaymentResponse;
import com.microservices.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/savePaymentDetail")
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.doPayment(paymentRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getByOrderId/{orderId}")
    public ResponseEntity<PaymentResponse> getpaymentDetailsByOrderId(@PathVariable("orderId") String orderId){
        return new ResponseEntity<>(paymentService.getorderByOrderId(orderId),HttpStatus.OK);
    }
}
