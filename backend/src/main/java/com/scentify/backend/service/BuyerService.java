package com.scentify.backend.service;

import com.scentify.backend.model.Buyer;
import com.scentify.backend.model.Cart;
import com.scentify.backend.model.Product;
import com.scentify.backend.repository.BuyerRepository;
import com.scentify.backend.repository.CartRepository;// nanti di sesuaikan namanya
import com.scentify.backend.repository.ProductRepository;// nanti di sesuaikan namanya
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepo;
    private final ProductRepository productRepo;
    private final CartRepository cartRepo;

    public BuyerService(BuyerRepository buyerRepo, ProductRepository productRepo, CartRepository cartRepo) {
        this.buyerRepo = buyerRepo;
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
    }

    public Optional<Buyer> getBuyerById(Long id) {
        return buyerRepo.findById(id);
    }

    public String addToCart(Long buyerId, String productId, int quantity) {
        Buyer buyer = buyerRepo.findById(buyerId).orElse(null);
            if (buyer == null) return "Buyer not found.";

        // Tambahkan langsung ke List<String> cart milik buyer
            for (int i = 0; i < quantity; i++) {
               buyer.getCart().add(productId);
            }

        buyerRepo.save(buyer); // simpan perubahan
        return "Product added to cart.";
    }

}
