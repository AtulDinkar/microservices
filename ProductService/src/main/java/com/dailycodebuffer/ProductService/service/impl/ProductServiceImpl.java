package com.dailycodebuffer.ProductService.service.impl;

import com.dailycodebuffer.ProductService.entity.Product;
import com.dailycodebuffer.ProductService.exception.ProductCustomExceptionHandler;
import com.dailycodebuffer.ProductService.model.ProductRequest;
import com.dailycodebuffer.ProductService.model.ProductResponse;
import com.dailycodebuffer.ProductService.repository.ProductRepository;
import com.dailycodebuffer.ProductService.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long saveProductData(ProductRequest productRequest) {
        log.info("adding product :: {}" + productRequest);

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice()).build();

        productRepository.save(product);

        log.info("product saved :: {}" + product);
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("get the product for product id :: {}" + productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductCustomExceptionHandler("Product not found ", "PRODUCT_NOT_FOUND") {
                        });

        ProductResponse productResponse = new ProductResponse();
        copyProperties(product, productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("reduce qty :: {} for id {}", quantity, productId);

        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ProductCustomExceptionHandler("Product not found", "PRODUCT_NOT_FOUND"));

        if (product.getQuantity() < quantity) {
            log.info("product does not have sufficient quantity");
            throw new ProductCustomExceptionHandler
                    ("product does not have sufficient quantity", "INSUFFICIENT_QUANTITY");
        }

        product.setQuantity(product.getQuantity()- quantity);
        productRepository.save(product);
    }
}
