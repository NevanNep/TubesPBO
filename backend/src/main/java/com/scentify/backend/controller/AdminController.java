package com.scentify.backend.controller;

import com.scentify.backend.model.Product;
import com.scentify.backend.model.Admin;
import com.scentify.backend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Endpoint untuk register admin baru
    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) {
        admin.setRole("ADMIN"); // Set paksa role admin
        Admin savedAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }


    // Tambah produk baru
    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        Product savedProduct = adminService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    // Update produk berdasar productId (String)
    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        try {
            Product product = adminService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Hapus produk berdasar productId (String)
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            adminService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get admin by id (Long)
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
