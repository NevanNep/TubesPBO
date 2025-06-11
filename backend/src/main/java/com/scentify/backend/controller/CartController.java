package com.scentify.backend.controller;

import com.scentify.backend.model.Cart;
import com.scentify.backend.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:8081")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Dapatkan cart berdasarkan buyerId
    @GetMapping("/{buyerId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long buyerId) {
        return ResponseEntity.ok(cartService.getCart(buyerId));
    }

    // Tambah item ke cart buyer tertentu
    @PostMapping("/{buyerId}/add")
    public ResponseEntity<String> addItem(
            @PathVariable Long buyerId,
            @RequestBody CartItemRequest request) {
        cartService.addItem(buyerId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok("Item added to cart.");
    }

    // Dapatkan total harga cart buyer tertentu
    @GetMapping("/{buyerId}/total")
    public ResponseEntity<Double> getTotal(@PathVariable Long buyerId) {
        return ResponseEntity.ok(cartService.calculateTotal(buyerId));
    }

    // Kosongkan cart buyer tertentu
    @PostMapping("/{buyerId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long buyerId) {
        cartService.clearCart(buyerId);
        return ResponseEntity.ok("Cart cleared.");
    }

    // DTO request body hanya mengandung productId dan quantity
    public static class CartItemRequest {
        private String productId;
        private int quantity;

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
