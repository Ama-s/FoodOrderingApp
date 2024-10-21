package com.ama.FoodOrdering.repos;

import com.ama.FoodOrdering.entities.OrderItem;
import com.ama.FoodOrdering.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository <Orders, Long> {
    // "Orders" This is the type of the entity the repository manages and "Long" is the type of the primary key of Orders entity

}
