package com.scentify.backend.service;

import com.scentify.backend.model.Admin;
import com.scentify.backend.model.Product;
import com.scentify.backend.repository.AdminRepository;
import com.scentify.backend.repository.ProductRepository; //nanti sesuain dengan nama repository product
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

    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    public Product updateProduct(Long productId, Product updatedProduct) {
        return productRepo.findById(productId.toString()).map(product -> {
            product.setNama(updatedProduct.getNama());
            product.setBrand(updatedProduct.getBrand());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            product.setCategory(updatedProduct.getCategory());
            return productRepo.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Long productId) {
        productRepo.deleteById(productId.toString());
    }

    public Optional<Admin> getAdminById(Long id) {
        return adminRepo.findById(id);
    }
}
