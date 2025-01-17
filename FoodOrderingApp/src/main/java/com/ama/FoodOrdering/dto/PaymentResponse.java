package com.ama.FoodOrdering.dto;

import com.ama.FoodOrdering.enums.PaymentStatus;

import java.time.LocalDate;

public class PaymentResponse {
    private Long id;
    private Long invoice_id;
    private LocalDate paymentIssueDate;
    private PaymentStatus status;

    // Constructors, getters, and setters
    public PaymentResponse(Long id, Long invoice_id, LocalDate paymentIssueDate, PaymentStatus status) {
        this.id = id;
        this.invoice_id = invoice_id;
        this.paymentIssueDate = paymentIssueDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(Long invoice_id) {
        this.invoice_id = invoice_id;
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

