package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
