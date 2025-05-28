package com.scentify.backend.repository;

import com.scentify.backend.model.Cart;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    private Cart cart = new Cart();

    public Cart getCart() {
        return cart;
    }

    public void saveCart(Cart cart) {
        this.cart = cart;
    }

    public void clearCart() {
        cart.setItems(new java.util.HashMap<>());
    }
}

    