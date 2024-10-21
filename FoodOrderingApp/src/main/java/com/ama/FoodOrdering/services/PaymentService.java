package com.ama.FoodOrdering.services;

import com.ama.FoodOrdering.entities.Payments;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    public Payments makePayment(Long invoice_id) throws ChangeSetPersister.NotFoundException;

    public Integer getTotalPaid(Long user_id) throws ChangeSetPersister.NotFoundException;

    public Integer getTotalOwed(Long user_id) throws ChangeSetPersister.NotFoundException;

    public Integer getTotalOverdue(Long user_id) throws ChangeSetPersister.NotFoundException;

    public List<Payments> getAccountSummary(Long user_id) throws ChangeSetPersister.NotFoundException;

    public List<Payments> getMonthlyBillByUser(Long user_id) throws ChangeSetPersister.NotFoundException;
}
