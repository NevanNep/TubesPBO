package com.scentify.backend.service;

import com.scentify.backend.model.Product;
import com.scentify.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Ambil semua produk
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Ambil satu produk berdasarkan ID
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    // Tambah produk baru
    public Product createProduct(Product product) {
        // Validasi sederhana (misal: default jika kosong)
        if (product.getName() == null || product.getName().isEmpty()) {
            product.setName("Unnamed Product");
        }
        if (product.getRating() == 0) {
            product.setRating(5); // default rating
        }
        return productRepository.save(product);
    }

    // Update data produk berdasarkan ID
    public Product updateProduct(String id, Product updatedProduct) {
        Optional<Product> existing = productRepository.findById(id);
        if (existing.isPresent()) {
            Product product = existing.get();
            product.setName(updatedProduct.getName());
            product.setBrand(updatedProduct.getBrand());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            product.setCategory(updatedProduct.getCategory());
            product.setDiscount(updatedProduct.getDiscount());
            product.setImage(updatedProduct.getImage());
            product.setRating(updatedProduct.getRating());
            return productRepository.save(product);
        } else {
            return null;
        }
    }

    // Hapus produk berdasarkan ID
    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
