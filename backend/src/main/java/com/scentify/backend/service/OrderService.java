package com.scentify.backend.service;

import com.scentify.backend.model.*;
import com.scentify.backend.repository.BuyerRepository;
import com.scentify.backend.repository.OrderRepository;
import com.scentify.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final BuyerRepository buyerRepo;
    private final ProductRepository productRepo;

    public OrderService(OrderRepository orderRepo, BuyerRepository buyerRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.buyerRepo = buyerRepo;
        this.productRepo = productRepo;
    }

    public Order createOrder(Long buyerId, Payment payMethod) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        List<String> cart = buyer.getCart();
        if (cart.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Hitung quantity masing-masing produk di cart
        Map<String, Long> productCount = cart.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        // Ambil daftar produk unik berdasarkan id di cart
        List<Product> products = productRepo.findAllById(productCount.keySet());

        // Buat list order item berdasarkan produk dan quantity
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (Product product : products) {
            long qty = productCount.getOrDefault(product.getId(), 0L);

            // Cek stok produk cukup
            if (product.getStock() < qty) { 
                throw new RuntimeException("Stok produk " + product.getId() + " tidak cukup");
            }

            // Buat OrderItem baru
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity((int) qty);
            item.setOrder(null); // nanti di set saat setItems di Order

            orderItems.add(item);

            totalAmount += product.getPrice() * qty;
        }

        // Buat order baru
        Order order = new Order();
        order.setId(generateOrderId());
        order.setBuyer(buyer);
        order.setItems(orderItems);
        order.setOrderDate(new Date());
        order.setStatus("PENDING");
        order.setPayMethod(payMethod);
        order.setTotalAmount(totalAmount);

        // Set back-reference order di setiap orderItem
        orderItems.forEach(oi -> oi.setOrder(order));

        // Proses pembayaran sederhana
        if (order.processPayment()) {
            order.setStatus("PAID");

            // Update stok produk sesuai quantity di orderItems
            for (OrderItem item : orderItems) {
                Product product = item.getProduct();
                int newStock = product.getStock() - item.getQuantity();
                product.setStock(newStock);
                productRepo.save(product);
            }

            // Kosongkan cart buyer
            buyer.getCart().clear();
            buyerRepo.save(buyer);
        } else {
            order.setStatus("PAYMENT_FAILED");
        }

        return orderRepo.save(order);
    }

    private String generateOrderId() {
        return "ORD" + System.currentTimeMillis();
    }

    public List<Order> getOrdersByBuyerId(Long buyerId) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        return orderRepo.findByBuyer(buyer);
    }
}
