package com.scentify.backend.controller;

import com.scentify.backend.model.Buyer;
import com.scentify.backend.service.BuyerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyer")
@CrossOrigin
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    // Endpoint untuk registrasi buyer baru dengan penanganan error
    @PostMapping("/register")
    public ResponseEntity<?> registerBuyer(@RequestBody Buyer buyer) {
        try {
            buyer.setRole("BUYER"); // Set paksa role buyer
            Buyer savedBuyer = buyerService.saveBuyer(buyer);
            return ResponseEntity.ok(savedBuyer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // Ambil data buyer berdasarkan id, response 404 jika tidak ditemukan
    @GetMapping("/{id}")
    public ResponseEntity<?> getBuyerById(@PathVariable Long id) {
        return buyerService.getBuyerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Tambah produk ke cart buyer dengan validasi stok dan error handling
    @PostMapping("/{buyerId}/cart/{productId}")
    public ResponseEntity<?> addToCart(
            @PathVariable Long buyerId,
            @PathVariable String productId,
            @RequestParam int quantity
    ) {
        try {
            String result = buyerService.addToCart(buyerId, productId, quantity);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Ambil isi cart buyer
    @GetMapping("/{buyerId}/cart")
    public ResponseEntity<List<String>> getCart(@PathVariable Long buyerId) {
        List<String> cart = buyerService.getCartByBuyerId(buyerId);
        return ResponseEntity.ok(cart);
    }

    // Update jumlah produk di cart buyer, bisa tambah atau kurang dengan validasi
    @PutMapping("/{buyerId}/cart/{productId}")
    public ResponseEntity<?> updateCartItemQuantity(
            @PathVariable Long buyerId,
            @PathVariable String productId,
            @RequestParam int quantity
    ) {
        try {
            String result = buyerService.updateCartItemQuantity(buyerId, productId, quantity);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // **Endpoint DELETE untuk menghapus produk dari cart buyer**
    @DeleteMapping("/{buyerId}/cart/{productId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable Long buyerId,
            @PathVariable String productId
    ) {
        try {
            String result = buyerService.removeFromCart(buyerId, productId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
