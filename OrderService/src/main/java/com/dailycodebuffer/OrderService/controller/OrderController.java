package com.dailycodebuffer.OrderService.controller;

import com.dailycodebuffer.OrderService.model.OrderRequest;
import com.dailycodebuffer.OrderService.model.OrderResponse;
import com.dailycodebuffer.OrderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        Long orderId = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }


    @GetMapping("/getOrderInfo/{orderId}")
    public ResponseEntity<OrderResponse> orderDetialsByOrderId(@PathVariable("orderId") long orderId)
    {
        OrderResponse orderResponse = orderService.getOrderDetailsById(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
