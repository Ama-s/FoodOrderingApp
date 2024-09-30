package com.ama.FoodOrdering.serviceImplementation;

import com.ama.FoodOrdering.entities.Invoice;
import com.ama.FoodOrdering.services.InvoiceService;
import jakarta.persistence.criteria.Order;

import java.util.List;
import java.util.UUID;

public class InvoiceServiceImp implements InvoiceService {
    @Override
    public Invoice generateInvoice(Order order) {
        return null;
    }

    @Override
    public void sendInvoice(Invoice invoice) {
        
    }

    @Override
    public List<Invoice> getInvoicesByUser(UUID user_id) {
        return List.of();
    }
}
