package com.ama.FoodOrdering.repos;

import com.ama.FoodOrdering.entities.Invoice;
import com.ama.FoodOrdering.entities.Orders;
import com.ama.FoodOrdering.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // In InvoiceRepository
    Optional<Invoice> findByOrderAndUser(Orders order, Users user);

    Set<Invoice> findByUser(Users user);
}
