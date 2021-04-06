package com.yakubovskiy.design.persistence;

import com.yakubovskiy.design.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductStorage {
    public Product persist(Product product) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public Optional<Product> load(UUID id) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public List<Product> loadAll() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
