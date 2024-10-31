package com.ama.FoodOrdering.repos;

import com.ama.FoodOrdering.entities.Payment;
import com.ama.FoodOrdering.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    @Query("SELECT p FROM Payment p JOIN p.invoice i WHERE i.user = :user")
    List<Payment> findByUser(@Param("user") User user);

    @Query("SELECT p FROM Payment p JOIN p.invoice i WHERE i.user = :user AND p.paymentIssueDate BETWEEN :startDate AND :endDate")
    List<Payment> findByUserAndDateBetween(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
