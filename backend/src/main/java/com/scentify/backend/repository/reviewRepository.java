package com.scentify.backend.repository;

import com.scentify.backend.model.review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface reviewRepository extends JpaRepository<review, String> {
    
    // Cari semua review berdasarkan ID produk
    List<review> findByProduct_ProductId(String productId);

    // Cari semua review berdasarkan ID buyer
    List<review> findByBuyer_BuyerId(String buyerId);

    // Cari semua review berdasarkan rating
    List<review> findByRating(int rating);
    
    // Optional: cari review berdasarkan username
    List<review> findByUserName(String userName);
}
