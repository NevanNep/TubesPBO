package com.scentify.backend.repository;

import com.scentify.backend.model.review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface reviewRepository extends JpaRepository<review, Long> {

    // Cari semua review berdasarkan ID produk
    List<review> findByProduct_ProductId(String productId);

    // Cari semua review berdasarkan ID buyer (ubah ke field `id` karena Buyer mewarisi dari User)
    List<review> findByBuyer_Id(Long id);

    // Cari semua review berdasarkan rating
    List<review> findByRating(int rating);

    // Optional: cari review berdasarkan username
    List<review> findByUserName(String userName);
}
