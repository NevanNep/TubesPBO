package com.scentify.backend.repository;

import com.scentify.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    // CRUD sudah tersedia otomatis, tinggal dipakai di service
    @Query("SELECT p FROM Product p LEFT JOIN OrderItem oi ON p.productId = oi.product.productId " +
       "GROUP BY p.productId ORDER BY SUM(oi.quantity) DESC")
    List<Product> findTopSellingProducts(Pageable pageable);
    List<Product> findByCategoryIgnoreCase(String category);
}
