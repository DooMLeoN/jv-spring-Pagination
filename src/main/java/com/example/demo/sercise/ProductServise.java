package com.example.demo.sercise;

import com.example.demo.model.Product;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

public interface ProductServise {
    Product save(Product product);

    List<Product> getAll();

    List<Product> findAllByPriseBetween(BigDecimal from, BigDecimal to);

    List<Product> getAll(PageRequest pageRequest);
}
