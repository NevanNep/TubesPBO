package com.scentify.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("BUYER")
public class Buyer extends User {

    @ElementCollection
    @CollectionTable(name = "buyer_order_history", joinColumns = @JoinColumn(name = "buyer_id"))
    @Column(name = "product_id")
    private List<String> orderHistory = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "buyer_cart", joinColumns = @JoinColumn(name = "buyer_id"))
    @Column(name = "product_id")
    private List<String> cart = new ArrayList<>();

    public Buyer() {
        // constructor kosong untuk JPA
    }

    public Buyer(Long id, String nama, String email, String password, String alamat, String role) {
        super(id, nama, email, password, alamat, role);
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

    public void setCart(List<String> cart) {
        this.cart = cart;
    }

    public List<String> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<String> orderHistory) {
        this.orderHistory = orderHistory;
    }
}
