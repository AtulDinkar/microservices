package com.dailycodebuffer.ProductService.service;

import com.dailycodebuffer.ProductService.model.ProductRequest;
import com.dailycodebuffer.ProductService.model.ProductResponse;

public interface ProductService {
    long saveProductData(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(long productId, long quantity);
}
