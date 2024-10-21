package com.ama.FoodOrdering.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "OrderItem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, referencedColumnName = "id")
    //indicates which column in the OrderItem table references the primary key of the Orders table
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false, referencedColumnName = "id")
    private MenuItem menuItem;

    @Column(name = "quantity")
    private Short quantity;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }
}
