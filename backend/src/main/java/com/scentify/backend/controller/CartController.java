package com.scentify.backend.controller;

import com.scentify.backend.dto.CartItemDTO;
import com.scentify.backend.dto.CheckoutRequestDTO;
import com.scentify.backend.model.Order;
import com.scentify.backend.service.BuyerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final BuyerService buyerService;

    public CartController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    // ✅ Ambil isi cart dengan detail produk dan quantity
    @GetMapping("/{buyerId}/items")
    public ResponseEntity<List<CartItemDTO>> getCartItems(@PathVariable Long buyerId) {
        return ResponseEntity.ok(buyerService.getCartItemsWithDetails(buyerId));
    }

    // ✅ Tambah produk ke cart (via JSON)
    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody CartItemRequest request) {
        String result = buyerService.addToCart(request.getBuyerId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(result);
    }

    // ✅ Update quantity produk di cart
    @PutMapping("/{buyerId}/cart/{productId}")
    public ResponseEntity<String> updateItemQty(@PathVariable Long buyerId,
                                                @PathVariable String productId,
                                                @RequestBody Map<String, Integer> body) {
        int quantity = body.getOrDefault("quantity", 1);
        String result = buyerService.updateCartItemQuantity(buyerId, productId, quantity);
        return ResponseEntity.ok(result);
    }

    // ✅ Hapus produk dari cart
    @DeleteMapping("/{buyerId}/cart/{productId}")
    public ResponseEntity<String> removeItem(@PathVariable Long buyerId,
                                             @PathVariable String productId) {
        String result = buyerService.removeFromCart(buyerId, productId);
        return ResponseEntity.ok(result);
    }

    // ✅ Hitung total harga
    @GetMapping("/{buyerId}/total")
    public ResponseEntity<Double> getTotal(@PathVariable Long buyerId) {
        double total = buyerService.getCartItemsWithDetails(buyerId).stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        return ResponseEntity.ok(total);
    }

    // ✅ Kosongkan keranjang
    @PostMapping("/{buyerId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long buyerId) {
        String result = buyerService.clearCart(buyerId);
        return ResponseEntity.ok(result);
    }

    // ✅ Checkout & Buat Order (pakai DTO)
    @PostMapping("/{buyerId}/checkout")
    public ResponseEntity<?> placeOrder(@PathVariable Long buyerId, @RequestBody CheckoutRequestDTO body) {
        try {
            String paymentMethod = body.getMethod();
            Order order = buyerService.placeOrder(buyerId, paymentMethod);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ DTO untuk JSON request tambah item
    public static class CartItemRequest {
        private Long buyerId;
        private String productId;
        private int quantity;

        public Long getBuyerId() {
            return buyerId;
        }

        public void setBuyerId(Long buyerId) {
            this.buyerId = buyerId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
