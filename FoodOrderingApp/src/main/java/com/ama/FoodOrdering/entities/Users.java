package com.ama.FoodOrdering.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;
import java.util.Set;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // to specify how the primary key should be generated

    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "role", length = 20)
    private String role;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @Column(name = "deleted_on")
    private LocalDateTime deletedOn;

    @Column(name = "deleted_by")
    private Long deletedBy;

    // OneToMany relationship with Orders
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders;
    // parent entity
    // "user" specifies the column/field in the Orders class that owns the relationship
    // For example In the Orders class, there would be a field like private Users user;

    // CascadeType.ALL specifies that any operations performed on the Users entity (like save, update, delete) will
    // also be applied to the associated Orders entities. So, if you delete a User, all associated Orders will also
    // be deleted.

    // When orphanRemoval is set to true, any Orders entity that is removed from the orders list in Users will also be
    // deleted from the database.
    // it reflects the one-to-many relationship. E.g. the list can contain the number of orders Deborah has

    @OneToMany(mappedBy = "user")
    private Set<Invoice> invoices;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(LocalDateTime deletedOn) {
        this.deletedOn = deletedOn;
    }

    public Long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }
}
