package com.yakubovskiy.design.service;

import com.yakubovskiy.design.domain.*;
import com.yakubovskiy.design.exception.ResourceNotFoundException;
import com.yakubovskiy.design.persistence.OrderItemStorage;
import com.yakubovskiy.design.persistence.OrderStorage;
import com.yakubovskiy.design.persistence.ProductStorage;
import com.yakubovskiy.design.persistence.UserStorage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
public class OrderService {
    public OrderStorage orderStorage;
    public UserStorage userStorage;
    public ProductStorage productStorage;
    public OrderItemStorage orderItemStorage;

    public OrderService(OrderStorage orderStorage, UserStorage userStorage, ProductStorage productStorage,
                        OrderItemStorage orderItemStorage) {
        this.orderStorage = orderStorage;
        this.userStorage = userStorage;
        this.productStorage = productStorage;
        this.orderItemStorage = orderItemStorage;
    }

    public Order placeOrder(@NonNull final UUID userId, @NonNull final String address) {
        User user = userStorage.load(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with user id " + userId));
        Order order = Order.of(user);
        order.setDate(LocalDate.now());
        order.setAddress(address);
        order.setOrderStatus(OrderStatus.UNDER_CONSIDERATION);
        log.info("Order {} was created", order);
        return orderStorage.persist(order);
    }

    public OrderItem addOrderItem(@NonNull final UUID orderId, @NonNull final UUID productId,
                                  final int quantity) {
        Product product = productStorage.load(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
        Order order = orderStorage.load(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
        List<OrderItem> orderItems = orderItemStorage.loadByOrderId(orderId);

        OrderItem orderItem = OrderItem.of(product, orderId);
        orderItem.setQuantity(quantity);
        int price = quantity * product.getPrice();
        orderItem.setPrice(price);

        orderItems.add(orderItem);
        order.setOrderItems(orderItems);

        log.info("Order item {} was added to order", orderItem);
        orderItemStorage.persist(orderItem);
        orderStorage.persist(order);
        return orderItem;
    }

    private Order updateOrder(@NonNull final UUID orderId) {
        Order order = orderStorage.load(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
        log.info("Order {} was received", order);
        List<OrderItem> orderItems = orderItemStorage.loadByOrderId(orderId);
        int totalPrice = 0;
        for (OrderItem item : orderItems) {
            totalPrice += item.getPrice();
        }
        order.setTotalPrice(totalPrice);
        order.setDate(LocalDate.now());
        return order;
    }

    public Order rejectOrder(@NonNull final UUID orderId) {
        Order order = updateOrder(orderId);
        order.setOrderStatus(OrderStatus.DENIED);
        log.info("Order {} was denied", order);
        return orderStorage.persist(order);
    }

    public Order produceOrder(@NonNull final UUID orderId) {
        Order order = updateOrder(orderId);
        order.setOrderStatus(OrderStatus.PRODUCED);
        log.info("Order {} was produced", order);
        return orderStorage.persist(order);
    }

    public List<Order> loadAllByUserId(@NonNull final UUID userId) {
        log.info("List of orders by user id was received");
        return orderStorage.loadAllByUserId(userId);
    }

    public void deleteOrderItem(@NonNull final UUID orderItemId) {
        OrderItem orderItem = orderItemStorage.load(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id " + orderItemId));
        log.info("Order item {} was received", orderItem);
        orderItemStorage.delete(orderItem.getId());
        log.info("Order item {} was deleted", orderItem);
    }
}
