package com.ama.FoodOrdering.dto;

import com.ama.FoodOrdering.enums.InvoiceStatus;

public class InvoiceResponse {
    private Long id;
    private Integer totalAmount;
    private InvoiceStatus status;
    private String generated_by;
    private Long order_id;


    public InvoiceResponse(Long id, Integer totalAmount, InvoiceStatus status, String generated_by, Long order_id) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.generated_by = generated_by;
        this.order_id = order_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long invoice_id) {
        this.id = invoice_id;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public String getGenerated_by() {
        return generated_by;
    }

    public void setGenerated_by(String generated_by) {
        this.generated_by = generated_by;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }
}