package com.scentify.backend.service;

import com.scentify.backend.model.Buyer;
import com.scentify.backend.repository.BuyerRepository;
import com.scentify.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepo;
    private final ProductRepository productRepo;

    public BuyerService(BuyerRepository buyerRepo, ProductRepository productRepo) {
        this.buyerRepo = buyerRepo;
        this.productRepo = productRepo;
    }

    public Optional<Buyer> getBuyerById(Long id) {
        return buyerRepo.findById(id);
    }

    public String addToCart(Long buyerId, String productId, int quantity) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        if (!productRepo.findById(productId).isPresent()) {
            throw new RuntimeException("Product not found");
        }

        for (int i = 0; i < quantity; i++) {
            buyer.getCart().add(productId);
        }

        buyerRepo.save(buyer); // Simpan perubahan cart
        return quantity + "x Produk " + productId + " ditambahkan ke keranjang!";
    }
}
