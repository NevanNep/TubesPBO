package com.scentify.backend.repository;

import com.scentify.backend.model.Buyer;
import com.scentify.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    // Bisa ditambah method custom jika perlu

    // Cari semua order berdasarkan buyer
    List<Order> findByBuyer(Buyer buyer);
}
