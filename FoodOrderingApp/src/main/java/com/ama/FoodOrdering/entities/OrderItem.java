package com.ama.FoodOrdering.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "OrderItem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_item_id", unique = true, nullable = false)
    private Long OrderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    //indicates which column in the OrderItem table references the primary key of the Orders table
    private Orders order;

    @Column(name = "menu_item_id")
    private Long menuItemId;

    @Column(name = "quantity")
    private Short quantity;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "modified_by")
    private UUID modifiedBy;

    @Column(name = "deleted_on")
    private LocalDateTime deletedOn;

    @Column(name = "deleted_by")
    private UUID deletedBy;

    // Getters and Setters
    // Getters and Setters

    public Long getOrderItemId() {
        return OrderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.OrderItemId = orderItemId;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public UUID getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UUID modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(LocalDateTime deletedOn) {
        this.deletedOn = deletedOn;
    }

    public UUID getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(UUID deletedBy) {
        this.deletedBy = deletedBy;
    }

}
