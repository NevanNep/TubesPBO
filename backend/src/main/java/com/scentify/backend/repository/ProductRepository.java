package com.scentify.backend.repository;

import com.scentify.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    // CRUD sudah tersedia otomatis, tinggal dipakai di service
}
