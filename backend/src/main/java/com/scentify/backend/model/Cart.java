package com.scentify.backend.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Product, Integer> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Product, Integer> items) {
        this.items = items;
    }

    public void addItem(Product product, int quantity) {
        items.merge(product, quantity, Integer::sum);
    }

    public double calculateTotal() {
        return items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public void showCart() {
        System.out.println("Isi Keranjang:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int qty = entry.getValue();
            System.out.println("- " + product.getName() + " x" + qty + " = Rp" + (product.getPrice() * qty));
        }
        System.out.println("Total: Rp" + calculateTotal());
    }

    // Optional: Tambahkan fungsi pengelolaan
    public void removeItem(Product product) {
        items.remove(product);
    }

    public void clear() {
        items.clear();
    }
}
