package com.scentify.backend.service;

import com.scentify.backend.model.Admin;
import com.scentify.backend.model.Product;
import com.scentify.backend.repository.AdminRepository;
import com.scentify.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepo;
    private final ProductRepository productRepo;

    public AdminService(AdminRepository adminRepo, ProductRepository productRepo) {
        this.adminRepo = adminRepo;
        this.productRepo = productRepo;
    }

    // Simpan admin baru
    public Admin saveAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

    // Tambah produk baru
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    // Update produk yang sudah ada
    public Product updateProduct(String productId, Product updatedProduct) {
        return productRepo.findById(productId).map(product -> {
            product.setName(updatedProduct.getName());
            product.setBrand(updatedProduct.getBrand());
            product.setImage(updatedProduct.getImage());
            product.setPrice(updatedProduct.getPrice());
            product.setDiscount(updatedProduct.getDiscount());
            product.setRating(updatedProduct.getRating());
            product.setStock(updatedProduct.getStock());
            product.setCategory(updatedProduct.getCategory());
            return productRepo.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Hapus produk
    public void deleteProduct(String productId) {
        productRepo.deleteById(productId);
    }

    // Ambil data admin berdasarkan ID
    public Optional<Admin> getAdminById(Long id) {
        return adminRepo.findById(id);
    }
}
