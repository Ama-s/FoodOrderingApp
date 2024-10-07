package com.ama.FoodOrdering.repos;

import com.ama.FoodOrdering.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
