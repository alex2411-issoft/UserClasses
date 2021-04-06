package com.yakubovskiy.design.persistence;

import com.yakubovskiy.design.domain.OrderItem;
import com.yakubovskiy.design.domain.Product;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 * Возможно неправильно понял смысл этих тестов (для persistence). Все тесты почти одинаковые вышли:)
 */

class OrderItemStorageTest {
    private final Product product;
    private final OrderItem orderItem;
    private final UUID id;

    private OrderItemStorage orderItemStorage = new OrderItemStorage();

    OrderItemStorageTest() {
        product = Product.of("test");
        orderItem = OrderItem.of(product, UUID.randomUUID());
        id = UUID.randomUUID();
    }

    @Test
    void persist() {
        assertThrows(UnsupportedOperationException.class, (() -> orderItemStorage.persist(orderItem)));
    }

    @Test
    void delete() {
        assertThrows(UnsupportedOperationException.class, (() -> orderItemStorage.delete(id)));
    }

    @Test
    void load() {
        assertThrows(UnsupportedOperationException.class, (() -> orderItemStorage.load(id)));
    }

    @Test
    void loadByOrderId() {
        assertThrows(UnsupportedOperationException.class, (() -> orderItemStorage.loadByOrderId(id)));
    }
}