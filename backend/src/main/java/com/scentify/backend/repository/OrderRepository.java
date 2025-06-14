package com.scentify.backend.repository;

import com.scentify.backend.model.Buyer;
import com.scentify.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    // Ambil semua pesanan dari buyer tertentu
    List<Order> findByBuyer(Buyer buyer);
}
