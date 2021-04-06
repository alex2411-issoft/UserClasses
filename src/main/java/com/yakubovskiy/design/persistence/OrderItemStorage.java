package com.yakubovskiy.design.persistence;

import com.yakubovskiy.design.domain.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderItemStorage {
    public OrderItem persist(OrderItem orderItem) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void delete(UUID orderItemId) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public Optional<OrderItem> load(UUID orderItemId) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public List<OrderItem> loadByOrderId(UUID orderId) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
