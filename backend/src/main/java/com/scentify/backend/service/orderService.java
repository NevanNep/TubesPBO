package com.scentify.backend.service;

import com.scentify.backend.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class orderService {

    private List<Order> orders = new ArrayList<>();

    public List<Order> getAllOrders() {
        return orders;
    }

    public Order getOrderById(String id) {
        Optional<Order> order = orders.stream()
                .filter(o -> o.getOrderId().equals(id))
                .findFirst();
        return order.orElse(null);
    }

    public Order createOrder(Order order) {
        orders.add(order);
        return order;
    }

    public Order updateOrder(String id, Order updatedOrder) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId().equals(id)) {
                orders.set(i, updatedOrder);
                return updatedOrder;
            }
        }
        return null;
    }

    public boolean deleteOrder(String id) {
        return orders.removeIf(order -> order.getOrderId().equals(id));
    }
}