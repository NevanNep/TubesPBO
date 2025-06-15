package com.scentify.backend.service;

import com.scentify.backend.model.Product;
import com.scentify.backend.repository.ProductRepository;
import com.scentify.backend.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public ProductService(ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
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
        if (product.getName() == null || product.getName().isEmpty()) {
            product.setName("Unnamed Product");
        }
        if (product.getRating() == 0) {
            product.setRating(5);
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

    // Ambil produk terlaris berdasarkan kuantitas order_items
    public List<Product> getBestSellers(int limit) {
        List<Object[]> results = orderItemRepository.findTopSellingProducts();
        List<Product> bestSellers = new ArrayList<>();

        for (Object[] row : results) {
            String productId = (String) row[0];
            productRepository.findById(productId).ifPresent(bestSellers::add);
            if (bestSellers.size() >= limit) break;
        }

        return bestSellers;
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }
}
