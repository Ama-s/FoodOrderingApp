package com.ama.FoodOrdering.services;

import com.ama.FoodOrdering.entities.Payments;
import java.util.List;
import java.util.UUID;

public interface PaymentsService {
    public Integer getTotalPaid();

    public Integer getTotalOwed();

    public List<Payments> getAccountSummary();

    public List<Payments> getMonthlyBillByUser(UUID user_id);
}
