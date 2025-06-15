package com.scentify.backend.repository;

import com.scentify.backend.model.Order;
import com.scentify.backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Cari semua item berdasarkan Order
    List<OrderItem> findByOrder(Order order);

    @Query("SELECT i.product.productId, SUM(i.quantity) as total FROM OrderItem i GROUP BY i.product.productId ORDER BY total DESC")
    List<Object[]> findTopSellingProducts();

}
