package com.yakubovskiy.design.persistence;

import com.yakubovskiy.design.domain.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderStorage {
    public Order persist(Order order) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public Optional<Order> load(UUID orderId) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public List<Order> loadAllByUserId(UUID orderId) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
