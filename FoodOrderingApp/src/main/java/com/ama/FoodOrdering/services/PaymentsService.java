package com.ama.FoodOrdering.services;

import com.ama.FoodOrdering.entities.Payments;
import java.util.List;
import java.util.UUID;

public interface PaymentsService {
    public int getTotalPaid();

    public int getTotalOwed();

    public List<Payments> getAccountSummary();

    public List<Payments> getMonthlyBillByUser(Long user_id);
}
