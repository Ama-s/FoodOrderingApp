package com.ama.FoodOrdering.services;
import com.ama.FoodOrdering.entities.Invoice;
import jakarta.persistence.criteria.Order;
import java.util.List;
import java.util.UUID;
public interface InvoiceService {

    public Invoice generateInvoice(Order order);

    public void sendInvoice(Invoice invoice);

    public List<Invoice> getInvoicesByUser(UUID user_id);
}