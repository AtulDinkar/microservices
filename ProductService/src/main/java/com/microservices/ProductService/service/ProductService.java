package com.microservices.ProductService.service;

import com.microservices.ProductService.model.ProductRequest;
import com.microservices.ProductService.model.ProductResponse;

public interface ProductService {
    long saveProductData(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(long productId, long quantity);
}
