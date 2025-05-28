package com.scentify.backend.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Import User if it's in another package, or define it if missing
import com.scentify.backend.model.User; // Change package if User is elsewhere

public class BuyerService extends User{
    private List<String> orderHistory = new ArrayList<>();
    private List<String> cart = new ArrayList<>();
    


    public BuyerService(Long id, String nama, String email, String password, String alamat) {
        super(id, nama, email, password, alamat);
    }

    public void addToCart(String productId, int quantity) {
        for (int i = 0; i < quantity; i++) {
            cart.add(productId);
        }
        System.out.println(quantity + "x Produk " + productId + " ditambahkan ke keranjang!");
    }

    public void checkout(String paymentMethod) {
        System.out.println("Checkout berhasil dengan " + paymentMethod);
        orderHistory.addAll(cart);
        cart.clear();
    }

    public Optional<Object> getBuyerById(Long id) {
        // TODO: Implement actual logic to fetch buyer by id
        return Optional.empty();
    }

    public List<String> getCart() {
        return cart;
    }

}