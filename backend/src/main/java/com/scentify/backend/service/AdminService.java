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

    // Tambah produk
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    // ✅ Update produk lengkap
    public Product updateProduct(String productId, Product updatedProduct) {
        return productRepo.findById(productId).map(product -> {
            product.setName(updatedProduct.getName());
            product.setBrand(updatedProduct.getBrand());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            product.setCategory(updatedProduct.getCategory());
            product.setDiscount(updatedProduct.getDiscount());  // Tambahan
            product.setImage(updatedProduct.getImage());        // Tambahan
            product.setRating(updatedProduct.getRating());      // Tambahan
            return productRepo.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Hapus produk
    public void deleteProduct(String productId) {
        productRepo.deleteById(productId);
    }

    // Ambil data admin by ID
    public Optional<Admin> getAdminById(Long id) {
        return adminRepo.findById(id);
    }
}
