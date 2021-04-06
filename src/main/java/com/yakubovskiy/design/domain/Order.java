package com.yakubovskiy.design.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Data
public class Order {
    private final UUID id;
    private final User user;
    private List<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private LocalDate date;
    private String address;
    private int totalPrice;

    public Order(UUID id, User user) {
        this.id = id;
        this.user = user;
    }

    public static Order of(@NonNull User user) {
        UUID id = UUID.randomUUID();
        return new Order(id, user);
    }


}
