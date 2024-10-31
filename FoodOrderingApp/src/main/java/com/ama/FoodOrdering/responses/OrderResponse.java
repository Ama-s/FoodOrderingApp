package com.ama.FoodOrdering.responses;

import com.ama.FoodOrdering.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public class OrderResponse {
    private Long id;
    private String ordered_by;

    private List<String> name;
    private List<Integer> price;
    private List<Short> quantity;

    private LocalDate order_date;
    private LocalDate due_date;
    private OrderStatus status;
    private Boolean isFavourite;

    public OrderResponse(Long id, String ordered_by, List<String> name, List<Integer> price, List<Short> quantity, LocalDate order_date, LocalDate due_date, OrderStatus status, Boolean isFavourite) {
        this.id = id;
        this.ordered_by = ordered_by;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.order_date = order_date;
        this.due_date = due_date;
        this.status = status;
        this.isFavourite = isFavourite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrdered_by() {
        return ordered_by;
    }

    public void setOrdered_by(String ordered_by) {
        this.ordered_by = ordered_by;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<Integer> getPrice() {
        return price;
    }

    public void setPrice(List<Integer> price) {
        this.price = price;
    }

    public List<Short> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Short> quantity) {
        this.quantity = quantity;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }

    public LocalDate getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }
}
