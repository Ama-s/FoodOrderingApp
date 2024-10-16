package com.ama.FoodOrdering.responses;

import com.ama.FoodOrdering.enums.PaymentStatus;

import java.time.LocalDate;

public class PaymentResponse {
    private LocalDate paymentIssueDate;
    private PaymentStatus status;

    // Constructors, getters, and setters
    public PaymentResponse(LocalDate paymentIssueDate, PaymentStatus status) {
        this.paymentIssueDate = paymentIssueDate;
        this.status = status;
    }

    public LocalDate getPaymentIssueDate() {
        return paymentIssueDate;
    }

    public void setPaymentIssueDate(LocalDate paymentIssueDate) {
        this.paymentIssueDate = paymentIssueDate;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}

