package com.scentify.backend.model;
import java.util.ArrayList;
import java.util.List;
public class Buyer extends User{
    private List<String> orderHistory = new ArrayList<>();
    private List<String> cart = new ArrayList<>();


    public Buyer(Long id, String nama, String email, String password, String alamat) {
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


    public List<String> getCart() {
        return cart;
    }

}
