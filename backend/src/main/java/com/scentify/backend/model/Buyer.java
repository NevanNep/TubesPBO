package com.scentify.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.DiscriminatorValue;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("BUYER")
public class Buyer extends User {

    @ElementCollection
    private List<String> orderHistory = new ArrayList<>();

    @ElementCollection
    private List<String> cart = new ArrayList<>();

    public Buyer() {
        // constructor kosong untuk JPA
    }

    public Buyer(Long id, String nama, String email, String password, String alamat) {
        super(id, nama, email, password, alamat);
    }

    public void addToCart(String productId, int quantity) {
        for (int i = 0; i < quantity; i++) {
            cart.add(productId);
        }
    }

    public void checkout(String paymentMethod) {
        orderHistory.addAll(cart);
        cart.clear();
    }

    public List<String> getCart() {
        return cart;
    }
}
