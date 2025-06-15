package com.scentify.backend.controller;

import com.scentify.backend.dto.CartItemDTO;
import com.scentify.backend.dto.CheckoutRequestDTO;
import com.scentify.backend.model.Buyer;
import com.scentify.backend.model.Order;
import com.scentify.backend.service.BuyerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    // ✅ Register buyer baru
    @PostMapping("/register")
    public ResponseEntity<?> registerBuyer(@RequestBody Buyer buyer) {
        try {
            buyer.setRole("BUYER");
            Buyer savedBuyer = buyerService.saveBuyer(buyer);
            return ResponseEntity.ok(savedBuyer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Ambil data buyer by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBuyerById(@PathVariable Long id) {
        return buyerService.getBuyerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Ambil isi cart buyer (dengan detail produk + qty)
    @GetMapping("/{buyerId}/cart")
    public ResponseEntity<List<CartItemDTO>> getCartItems(@PathVariable Long buyerId) {
        List<CartItemDTO> cartItems = buyerService.getCartItemsWithDetails(buyerId);
        return ResponseEntity.ok(cartItems);
    }

    // ✅ Checkout (pakai DTO dan kembalikan Order)
    @PostMapping("/{buyerId}/order")
    public ResponseEntity<?> placeOrder(@PathVariable Long buyerId, @RequestBody CheckoutRequestDTO req) {
        try {
            Order order = buyerService.placeOrder(buyerId, req.getMethod());
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{buyerId}/orders")
    public ResponseEntity<?> getOrderHistory(@PathVariable Long buyerId) {
        return ResponseEntity.ok(buyerService.getOrderHistory(buyerId));
    }

}
