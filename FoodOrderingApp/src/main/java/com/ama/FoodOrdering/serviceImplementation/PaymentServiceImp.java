package com.ama.FoodOrdering.serviceImplementation;

import com.ama.FoodOrdering.entities.Payments;
import com.ama.FoodOrdering.services.PaymentsService;

import java.util.List;
import java.util.UUID;

public class PaymentServiceImp implements PaymentsService {
    @Override
    public Integer getTotalPaid() {
        return 0;
    }

    @Override
    public Integer getTotalOwed() {
        return 0;
    }

    @Override
    public List<Payments> getAccountSummary() {
        return List.of();
    }

    @Override
    public List<Payments> getMonthlyBillByUser(UUID user_id) {
        return List.of();
    }
}
