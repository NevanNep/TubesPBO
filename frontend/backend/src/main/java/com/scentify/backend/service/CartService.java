package com.scentify.backend.service;

import com.scentify.backend.model.Cart;
import com.scentify.backend.model.Product;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private Cart cart = new Cart();

    public Cart getCart() {
        return cart;
    }

    public void addItem(Product product, int quantity) {
        cart.addItem(product, quantity);
    }

    public double calculateTotal() {
        return cart.calculateTotal();
    }

    public void clearCart() {
        cart.setItems(new java.util.HashMap<>());
    }
}