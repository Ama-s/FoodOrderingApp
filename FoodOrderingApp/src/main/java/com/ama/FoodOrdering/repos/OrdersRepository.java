package com.ama.FoodOrdering.repos;

import com.ama.FoodOrdering.entities.OrderItem;
import com.ama.FoodOrdering.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository <Order, Long> {
    // "Orders" This is the type of the entity the repository manages and "Long" is the type of the primary key of Orders entity

}
