package com.yakubovskiy.design.domain;

import com.google.common.base.Preconditions;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Data
public class Product {
    private final UUID id;
    private String title;
    private int price;

    private Product(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    public static Product of(@NonNull String title) {
        UUID id = UUID.randomUUID();
        return new Product(id, title);
    }

}
