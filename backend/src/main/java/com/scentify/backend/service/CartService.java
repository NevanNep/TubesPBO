package com.scentify.backend.service;

import com.scentify.backend.model.Cart;
import com.scentify.backend.model.Product;
import com.scentify.backend.repository.ProductRepository;
import com.scentify.backend.repository.BuyerRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    private final ProductRepository productRepo;
    private final BuyerRepository buyerRepo;

    // Map untuk simpan cart per buyer
    private Map<Long, Cart> buyerCarts = new HashMap<>();

    public CartService(ProductRepository productRepo, BuyerRepository buyerRepo) {
        this.productRepo = productRepo;
        this.buyerRepo = buyerRepo;
    }

    public Cart getCart(Long buyerId) {
        return buyerCarts.getOrDefault(buyerId, new Cart());
    }

    public void addItem(Long buyerId, String productId, int quantity) {
        buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer tidak ditemukan"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produk tidak ditemukan"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Stok produk tidak cukup");
        }

        Cart cart = buyerCarts.computeIfAbsent(buyerId, k -> new Cart());
        cart.addItem(product, quantity);

        // Update cart di map
        buyerCarts.put(buyerId, cart);
    }

    public double calculateTotal(Long buyerId) {
        Cart cart = buyerCarts.get(buyerId);
        return (cart == null) ? 0 : cart.calculateTotal();
    }

    public void clearCart(Long buyerId) {
        buyerCarts.remove(buyerId);
    }
}
