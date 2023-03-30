package com.example.demo.sercise;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductServiseImpl implements ProductServise {
    private final ProductRepository repository;

    public ProductServiseImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> findAllByPriseBetween(BigDecimal from, BigDecimal to) {
        return repository.findAllByPriseBetween(from, to);
    }
}
