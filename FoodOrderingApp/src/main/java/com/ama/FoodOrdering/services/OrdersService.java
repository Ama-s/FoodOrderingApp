package com.ama.FoodOrdering.services;

import com.ama.FoodOrdering.entities.OrderItem;
import com.ama.FoodOrdering.entities.Orders;
import com.ama.FoodOrdering.entities.Payments;
import java.util.List;
import java.util.UUID;

public interface OrdersService {
    public List<OrderItem> placeOrder();

    public OrderItem addOrderItem();

    public void deleteOrder();

    public List<Orders> getOrderHistory();

    public OrderItem markFavourite();

    public int getTotalQuantityOrdered();

    public Orders viewPastOrdersByUser(UUID user_id);
}
