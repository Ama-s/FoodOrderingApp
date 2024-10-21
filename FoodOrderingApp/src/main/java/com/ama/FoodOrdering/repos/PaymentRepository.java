package com.ama.FoodOrdering.repos;

import com.ama.FoodOrdering.entities.Payments;
import com.ama.FoodOrdering.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payments, Long>{
    @Query("SELECT p FROM Payments p JOIN p.invoice i WHERE i.user = :user")
    List<Payments> findByUser(@Param("user") Users user);

    @Query("SELECT p FROM Payments p JOIN p.invoice i WHERE i.user = :user AND p.paymentIssueDate BETWEEN :startDate AND :endDate")
    List<Payments> findByUserAndDateBetween(@Param("user") Users user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
