package com.example.demo.controller;

import com.example.demo.dto.ProductRequestDto;
import com.example.demo.dto.ProductResponseDto;
import com.example.demo.dto.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.sercise.ProductServise;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductServise productServise;
    private final ProductMapper productMapper;

    public ProductController(ProductServise productServise, ProductMapper productMapper) {
        this.productServise = productServise;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ProductResponseDto crate(@RequestBody ProductRequestDto responseDto) {
        Product product = productServise.save(productMapper.toModel(responseDto));
        return productMapper.toResponseDto(product);
    }

    @GetMapping
    public List<ProductResponseDto> getAll() {
       return productServise.getAll()
               .stream()
               .map(productMapper::toResponseDto)
               .collect(Collectors.toList());
    }

    @GetMapping("/by-prise")
        public List<ProductResponseDto> getProductByPriseBetween(@RequestParam BigDecimal from,
                                                                 @RequestParam BigDecimal to) {
        return productServise.findAllByPriseBetween(from, to)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
