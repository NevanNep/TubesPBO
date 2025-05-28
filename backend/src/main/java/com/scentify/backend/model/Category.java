package com.scentify.backend.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String categoryId;
    private String nama;
    private List<Product> products;

    public Category(String categoryId, String nama) {
        this.categoryId = categoryId;
        this.nama = nama;
        this.products = new ArrayList<>();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<Product> getProducts() {
        return products;
    }


    public void addProduct(Product product) {
        if (product != null && !products.contains(product)) {
            products.add(product);
        }
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", nama='" + nama + '\'' +
                ", totalProducts=" + products.size() +
                '}';
    }
}