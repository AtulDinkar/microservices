package com.microservices.ProductService.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @Column
    private String productName;
    @Column
    private Long price;
    @Column
    private Long quantity;
}
