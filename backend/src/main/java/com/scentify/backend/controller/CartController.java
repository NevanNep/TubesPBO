package com.scentify.backend.controller;

import com.scentify.backend.model.Cart;
import com.scentify.backend.model.Product;
import com.scentify.backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody CartItemRequest request) {
        cartService.addItem(request.getProduct(), request.getQuantity());
        return ResponseEntity.ok("Item added to cart.");
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotal() {
        return ResponseEntity.ok(cartService.calculateTotal());
    }

    @PostMapping("/clear")
    public ResponseEntity<String> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("Cart cleared.");
    }

    // DTO untuk request body
    public static class CartItemRequest {
        private Product product;
        private int quantity;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}