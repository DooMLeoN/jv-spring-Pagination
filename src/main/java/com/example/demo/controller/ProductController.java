package com.example.demo.controller;

import com.example.demo.dto.ProductRequestDto;
import com.example.demo.dto.ProductResponseDto;
import com.example.demo.dto.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.sercise.ProductServise;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @PostMapping("/inject")
    public String inject() {

        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setTitle(("iPhone " + i));
            product.setPrise(BigDecimal.valueOf(100 + i));
            productServise.save(product);
        }
        return "inject!";
    }
    @PostMapping
    public ProductResponseDto crate(@RequestBody ProductRequestDto responseDto) {
        Product product = productServise.save(productMapper.toModel(responseDto));
        return productMapper.toResponseDto(product);
    }

    @GetMapping
    public List<ProductResponseDto> getAll(@RequestParam (defaultValue = "20") Integer count,
                                           @RequestParam (defaultValue = "0") Integer page,
                                           @RequestParam (defaultValue = "id") String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if(sortBy.contains(":")) {
            String[] scoringFilter = sortBy.split(";");
            for(String field : scoringFilter) {
                Sort.Order order;
                if (field.contains(":")) {
                    String[] fieldsAndDirections = field.split(":");
                    order = new Sort.Order(Sort.Direction.valueOf(fieldsAndDirections[1]),
                            fieldsAndDirections[0]);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, field);
                }
                orders.add(order);
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }

        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return productServise.getAll(pageRequest)
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
