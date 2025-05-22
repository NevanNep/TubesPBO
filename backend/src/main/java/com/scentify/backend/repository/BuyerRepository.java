package com.scentify.backend.repository;

import com.scentify.backend.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    // Bisa ditambahkan query seperti findByEmail(String email) kalau perlu
}
