package com.scentify.backend.service;

import com.scentify.backend.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(String id) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst();
        return product.orElse(null);
    }

    public Product createProduct(Product product) {
        products.add(product);
        return product;
    }

    public Product updateProduct(String id, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(id)) {
                products.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }

    public boolean deleteProduct(String id) {
        return products.removeIf(product -> product.getProductId().equals(id));
    }
}