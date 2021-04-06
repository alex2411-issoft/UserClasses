package com.yakubovskiy.design.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Data
public class OrderItem {
    private final UUID id;
    private final UUID orderId;
    private final Product product;
    private int quantity;
    private int price;

    public OrderItem(UUID id, UUID orderId, Product product) {
        this.id = id;
        this.orderId = orderId;
        this.product = product;
    }

    public static OrderItem of(@NonNull final Product product, @NonNull final UUID orderId) {
        UUID id = UUID.randomUUID();
        return new OrderItem(id, orderId, product);
    }

}
