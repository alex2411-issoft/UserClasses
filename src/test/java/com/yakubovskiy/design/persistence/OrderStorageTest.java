package com.yakubovskiy.design.persistence;

import com.yakubovskiy.design.domain.Order;
import com.yakubovskiy.design.domain.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 * Возможно неправильно понял смысл этих тестов (для persistence). Все тесты почти одинаковые вышли:)
 */

class OrderStorageTest {
    private OrderStorage orderStorage = new OrderStorage();
    private final Order order;
    private final User user;
    private final UUID id;

    OrderStorageTest() {
        user = User.of("alex2411","Alex","Ivanov");
        order = Order.of(user);
        id = UUID.randomUUID();
    }

    @Test
    void persist() {
        assertThrows(UnsupportedOperationException.class, (() -> orderStorage.persist(order)));
    }

    @Test
    void load() {
        assertThrows(UnsupportedOperationException.class, (() -> orderStorage.load(id)));
    }

    @Test
    void loadAllByUserId() {
        assertThrows(UnsupportedOperationException.class, (() -> orderStorage.loadAllByUserId(id)));
    }
}