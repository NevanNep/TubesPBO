package com.scentify.backend.model;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private String productId;
    private String nama;
    private String brand;
    private double price;
    private int stock;
    private Category category;
    private List<Review> reviews;

    public Product(String productId, String nama, String brand, double price, int stock, Category category) {
        this.productId = productId;
        this.nama = nama;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.reviews = new ArrayList<>();
    }

    // Getter dan Setter
    public String getProductId() {
        return productId;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    // Method sesuai diagram
    public void updateStock(int newStock) {
        this.stock = newStock;
    }

    public void applyDiscount(double percentage) {
        if (percentage > 0 && percentage <= 100) {
            this.price -= this.price * (percentage / 100.0);
        }
    }

    public static List<Product> searchByName(List<Product> products, String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getNama().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(product);
            }
        }
        return result;
    }

    public static List<Product> searchByBrand(List<Product> products, String brand) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getBrand().equalsIgnoreCase(brand)) {
                result.add(product);
            }
        }
        return result;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }
}