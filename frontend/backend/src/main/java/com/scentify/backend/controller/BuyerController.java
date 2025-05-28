package com.scentify.backend.controller;


import com.scentify.backend.service.BuyerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer")
@CrossOrigin
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBuyerById(@PathVariable Long id) {
        return buyerService.getBuyerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{buyerId}/cart/{productId}")
    public ResponseEntity<?> addToCart(
            @PathVariable Long buyerId,
            @PathVariable Long productId,
            @RequestParam int quantity
    ) {
        buyerService.addToCart(buyerId, String.valueOf(productId), quantity);
        return ResponseEntity.ok("Product added to cart successfully.");
    }
}
