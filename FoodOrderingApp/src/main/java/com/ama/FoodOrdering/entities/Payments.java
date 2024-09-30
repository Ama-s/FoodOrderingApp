package com.ama.FoodOrdering.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "status", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'pending'")
    private String status;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
