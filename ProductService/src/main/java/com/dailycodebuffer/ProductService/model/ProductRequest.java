package com.dailycodebuffer.ProductService.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ProductRequest {
    private String productName;
    private Long price;
    private Long quantity;
}
