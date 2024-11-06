package com.ama.FoodOrdering.services;

import com.ama.FoodOrdering.entities.Payment;
import com.ama.FoodOrdering.responses.PaymentResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import java.util.List;

public interface PaymentService {
    public Payment makePayment(Long invoice_id) throws ChangeSetPersister.NotFoundException;

    public Integer getTotalPaid() throws ChangeSetPersister.NotFoundException;

    public Integer getTotalOwed() throws ChangeSetPersister.NotFoundException;

    public Integer getTotalOverdue() throws ChangeSetPersister.NotFoundException;

    public List<PaymentResponse> getAccountSummary() throws ChangeSetPersister.NotFoundException;

}
