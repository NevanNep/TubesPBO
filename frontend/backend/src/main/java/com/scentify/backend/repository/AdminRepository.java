package com.scentify.backend.repository;

// Update the import below to match the actual package of Admin class
// For example, if Admin is in com.scentify.backend.model, use:
import com.scentify.backend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    // Tambahkan custom query jika diperlukan
}