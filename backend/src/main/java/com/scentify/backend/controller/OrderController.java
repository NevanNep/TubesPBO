package com.scentify.backend.controller;

import com.scentify.backend.model.Order;
import com.scentify.backend.model.Payment;
import com.scentify.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/buyer")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Buat order baru untuk buyer tertentu dengan payment method lewat JSON body
    @PostMapping("/{buyerId}/order")
    public ResponseEntity<?> createOrder(
            @PathVariable Long buyerId,
            @RequestBody Payment paymentMethod
    ) {
        try {
            Order order = orderService.createOrder(buyerId, paymentMethod);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{buyerId}/orders")
public ResponseEntity<?> getOrderHistory(@PathVariable Long buyerId) {
    try {
        List<Order> orders = orderService.getOrdersByBuyerId(buyerId);
        return ResponseEntity.ok(orders);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

    
}
