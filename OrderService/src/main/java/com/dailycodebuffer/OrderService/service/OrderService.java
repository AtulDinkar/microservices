package com.dailycodebuffer.OrderService.service;

import com.dailycodebuffer.OrderService.model.OrderRequest;
import com.dailycodebuffer.OrderService.model.OrderResponse;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetailsById(long orderId);
}
