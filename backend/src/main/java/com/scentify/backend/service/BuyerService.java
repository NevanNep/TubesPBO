package com.scentify.backend.service;

import com.scentify.backend.dto.CartItemDTO;
import com.scentify.backend.dto.ItemBriefDTO;
import com.scentify.backend.dto.OrderHistoryDTO;
import com.scentify.backend.model.*;
import com.scentify.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final PaymentRepository paymentRepo;

    public BuyerService(BuyerRepository buyerRepo, ProductRepository productRepo,
                        OrderRepository orderRepo, OrderItemRepository orderItemRepo,
                        PaymentRepository paymentRepo) {
        this.buyerRepo = buyerRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.paymentRepo = paymentRepo;
    }

    public Buyer saveBuyer(Buyer buyer) {
        return buyerRepo.save(buyer);
    }

    public Optional<Buyer> getBuyerById(Long id) {
        return buyerRepo.findById(id);
    }

    public String addToCart(Long buyerId, String productId, int quantity) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Stok produk tidak cukup");
        }

        for (int i = 0; i < quantity; i++) {
            buyer.getCart().add(productId);
        }

        buyerRepo.save(buyer);
        return quantity + "x Produk " + productId + " ditambahkan ke keranjang!";
    }

    public List<CartItemDTO> getCartItemsWithDetails(Long buyerId) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        List<String> cartIds = buyer.getCart();
        Map<String, Long> countMap = cartIds.stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        List<CartItemDTO> cartItems = new ArrayList<>();

        for (Map.Entry<String, Long> entry : countMap.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue().intValue();
            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Produk tidak ditemukan: " + productId));

            // ✅ Perhitungan harga diskon diterapkan di sini
            double price = product.getPrice();
            if (product.getDiscount() > 0) {
                price = Math.round(product.getPrice() * (1 - product.getDiscount() / 100.0));
            }

            cartItems.add(new CartItemDTO(
                    product.getProductId(),
                    product.getName(),
                    product.getImage(),
                    price,
                    quantity
            ));
        }

        return cartItems;
    }

    public List<CartItemDTO> getCartByBuyerId(Long buyerId) {
        return getCartItemsWithDetails(buyerId);
    }

    public String updateCartItemQuantity(Long buyerId, String productId, int newQuantity) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        long currentQuantity = buyer.getCart().stream()
                .filter(id -> id.equals(productId))
                .count();

        int difference = newQuantity - (int) currentQuantity;

        if (difference > 0) {
            if (product.getStock() < difference) {
                throw new RuntimeException("Stok produk tidak cukup");
            }
            for (int i = 0; i < difference; i++) {
                buyer.getCart().add(productId);
            }
        } else if (difference < 0) {
            int toRemove = Math.abs(difference);
            for (int i = 0; i < toRemove; i++) {
                if (!buyer.getCart().remove(productId)) {
                    break;
                }
            }
        }

        buyerRepo.save(buyer);
        return "Jumlah produk " + productId + " di keranjang diperbarui menjadi " + newQuantity;
    }

    public String removeFromCart(Long buyerId, String productId) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        boolean removed = buyer.getCart().removeIf(id -> id.equals(productId));

        if (!removed) {
            throw new RuntimeException("Product not found in cart");
        }

        buyerRepo.save(buyer);
        return "Produk " + productId + " telah dihapus dari keranjang.";
    }

    public String clearCart(Long buyerId) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        buyer.getCart().clear();
        buyerRepo.save(buyer);
        return "Keranjang berhasil dikosongkan.";
    }

    public Order placeOrder(Long buyerId, String paymentMethod) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        List<CartItemDTO> cartItems = getCartItemsWithDetails(buyerId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart kosong");
        }

        for (CartItemDTO item : cartItems) {
            Product product = productRepo.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produk tidak ditemukan: " + item.getProductId()));
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Stok tidak cukup untuk: " + product.getName());
            }
        }

        Order order = new Order();
        order.setId("ORD" + System.currentTimeMillis());
        order.setBuyer(buyer);
        order.setOrderDate(new Date());
        order.setStatus("PAID");
        order.setTotalAmount(cartItems.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum());

        Payment payment = new Payment();
        payment.setMethod(paymentMethod);
        payment.setStatus("SUCCESS");
        payment.setAmount(order.getTotalAmount());
        paymentRepo.save(payment);

        order.setPayMethod(payment);

        List<OrderItem> items = new ArrayList<>();
        for (CartItemDTO item : cartItems) {
            Product product = productRepo.findById(item.getProductId()).get();
            product.setStock(product.getStock() - item.getQuantity());
            productRepo.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(order);
            items.add(orderItem);
        }

        order.setItems(items);
        orderRepo.save(order);

        buyer.getCart().clear();
        buyerRepo.save(buyer);

        return order;
    }

    public List<OrderHistoryDTO> getOrderHistory(Long buyerId) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        List<Order> orders = orderRepo.findByBuyer(buyer);
        List<OrderHistoryDTO> history = new ArrayList<>();

        for (Order o : orders) {
            List<ItemBriefDTO> items = o.getItems().stream()
                    .map(i -> new ItemBriefDTO(i.getProduct().getProductId(), i.getProduct().getName(), i.getQuantity()))
                    .collect(Collectors.toList());
            OrderHistoryDTO dto = new OrderHistoryDTO(o.getId(), o.getOrderDate(), o.getTotalAmount(), o.getStatus(), items);
            history.add(dto);
        }

        return history;
    }
}
