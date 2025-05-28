package com.scentify.backend.repository;

import com.scentify.backend.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private List<Product> products = new ArrayList<>();

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(String id) {
        return products.stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst();
    }

    public Product save(Product product) {
        products.add(product);
        return product;
    }

    public void deleteById(String id) {
        products.removeIf(product -> product.getProductId().equals(id));
    }

    public Product update(String id, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(id)) {
                products.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }
}