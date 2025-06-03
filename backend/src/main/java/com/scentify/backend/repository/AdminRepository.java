package com.scentify.backend.repository;

import com.scentify.backend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    // Bisa tambahkan query custom kalau perlu, tapi ini sudah cukup untuk CRUD dasar
}
