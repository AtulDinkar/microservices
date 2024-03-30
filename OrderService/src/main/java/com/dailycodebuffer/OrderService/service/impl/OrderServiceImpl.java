package com.dailycodebuffer.OrderService.service.impl;

import com.dailycodebuffer.OrderService.entity.Order;
import com.dailycodebuffer.OrderService.exception.OrderServiceCustomException;
import com.dailycodebuffer.OrderService.external.client.PaymentService;
import com.dailycodebuffer.OrderService.external.client.ProductService;
import com.dailycodebuffer.OrderService.external.request.PaymentRequest;
import com.dailycodebuffer.OrderService.external.response.PaymentResponse;
import com.dailycodebuffer.OrderService.model.OrderRequest;
import com.dailycodebuffer.OrderService.model.OrderResponse;
import com.dailycodebuffer.OrderService.model.ProductResponse;
import com.dailycodebuffer.OrderService.repository.OrderRepository;
import com.dailycodebuffer.OrderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Long placeOrder(OrderRequest orderRequest) {

        //product service -> to block products(reduce quantity)
        log.info("reducing order by using reduceQuantity method from productService by from order service");
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("placing order for :: {}" + orderRequest);
        //order entity -> to save order in db
        Order order = Order.builder()
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .totalAmount(orderRequest.getTotalAmount()).build();

        order = orderRepository.save(order);
        log.info("order placed successfully for order id : {}" + order.getId());

        //payment Service -> for payment -> success or failed
        log.info("calling doPayment method from paymentservice to do payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount()).build();

        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("payment done successfully changing the order status to placed");
            orderStatus = "PLACED";
        } catch (Exception e) {
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        return order.getId();


        //cancelled
    }

    @Override
    public OrderResponse getOrderDetailsById(long orderId) {
        log.info("get order details for id :: {}", orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new OrderServiceCustomException("Order not found for this id :: " + orderId, "ORDER_FOUND", 404));

        log.info("invoking product service for getting product details for order id :: {} " + orderId);

        ProductResponse productResponse =
                restTemplate.getForObject("http://PRODUCT-SERVICE/product/getById/" + order.getProductId(), ProductResponse.class);

        OrderResponse.ProductDetails orderProductResponse = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .price(productResponse.getPrice())
                .build();

        log.info("getting payment info from payment service");
        PaymentResponse paymentResponse = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/getByOrderId/" + orderId,
                PaymentResponse.class);
        OrderResponse.PaymentDetails orderPaymentDetails = OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentStatus(paymentResponse.getPaymentStatus())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getTotalAmount())
                .orderDate(order.getOrderDate())
                .productDetails(orderProductResponse)
                .paymentDetails(orderPaymentDetails)
                .build();

        return orderResponse;
    }
}
