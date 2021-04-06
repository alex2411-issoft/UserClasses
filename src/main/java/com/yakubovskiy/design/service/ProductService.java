package com.yakubovskiy.design.service;

import com.yakubovskiy.design.domain.Product;
import com.yakubovskiy.design.exception.ResourceNotFoundException;
import com.yakubovskiy.design.persistence.ProductStorage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
public class ProductService {
    private final ProductStorage productStorage;

    public ProductService(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public List<Product> findAllProducts() {
        log.info("List of products was received");
        return productStorage.loadAll();
    }

    public Product findProductById(@NonNull UUID id) {
        Product product = productStorage.load(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        log.info("Product {} was received", product);
        return product;
    }

    public Product addProduct(@NonNull Product product) {
        log.info("Product {} was added", product);
        return productStorage.persist(product);
    }

    public Product editProduct(@NonNull UUID id, @NonNull Product editProduct) {
        Product product = findProductById(id);

        product.setPrice(editProduct.getPrice());
        product.setTitle(editProduct.getTitle());

        log.info("Product {} was edited", product);
        return productStorage.persist(product);
    }
}
