package com.ama.FoodOrdering.repos;

import com.ama.FoodOrdering.entities.Invoice;
import com.ama.FoodOrdering.entities.Order;
import com.ama.FoodOrdering.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // In InvoiceRepository
    Optional<Invoice> findByOrderAndUser(Order order, User user);

    Set<Invoice> findByUser(User user);
}
