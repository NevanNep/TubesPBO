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

    public String addToCart(Long buyerId, Long productId, int quantity) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = buyer.getCart();
        cart.getItems().put(product, quantity); // sesuaikan dengan struktur cart-mu
        cartRepo.save(cart);

        return "Product added to cart.";
    }
}
