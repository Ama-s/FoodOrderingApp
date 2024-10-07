package com.ama.FoodOrdering.services;
import com.ama.FoodOrdering.entities.OrderItem;
import com.ama.FoodOrdering.entities.Orders;
import com.ama.FoodOrdering.entities.Payments;
import com.ama.FoodOrdering.entities.Users;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.UUID;
public interface OrdersService {
    public Orders placeOrder(List<OrderItem> orderItems, Long user_id) throws ChangeSetPersister.NotFoundException;

    public OrderItem addOrderItem();

    public void deleteOrder();

    public List<Orders> getOrderHistory();

    public OrderItem markFavourite();

    public int getTotalQuantityOrdered();

    public Orders viewPastOrdersByUser(UUID user_id);
}