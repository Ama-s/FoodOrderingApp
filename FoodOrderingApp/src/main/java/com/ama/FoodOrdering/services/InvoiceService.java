package com.ama.FoodOrdering.services;
import com.ama.FoodOrdering.entities.Invoice;
import com.ama.FoodOrdering.entities.Orders;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Set;
import java.util.UUID;
public interface InvoiceService {

    public Invoice generateInvoice(Long order_id, Long user_id) throws ChangeSetPersister.NotFoundException;

    public Invoice getInvoice(Long order_id, Long user_id) throws ChangeSetPersister.NotFoundException;

    public Set<Invoice> getAllInvoice(Long user_id) throws ChangeSetPersister.NotFoundException;
}