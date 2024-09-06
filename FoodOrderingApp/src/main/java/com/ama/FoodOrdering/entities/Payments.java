package com.ama.FoodOrdering.entities;

import com.ama.FoodOrdering.enums.PaymentStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    @Column(name = "payment_issue_date")
    private LocalDate paymentIssueDate;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
    private PaymentStatus status;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long paymentId) {
        this.id = paymentId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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
