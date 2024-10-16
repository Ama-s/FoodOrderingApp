package com.ama.FoodOrdering.services;
import com.ama.FoodOrdering.entities.OrderItem;
import com.ama.FoodOrdering.entities.Orders;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;
public interface OrdersService {
    public Orders placeOrder(List<OrderItem> orderItems, Long user_id) throws ChangeSetPersister.NotFoundException;

    // public OrderItem addOrderItem(OrderItem item, Long order_id, Long user_id);
    // I don't think there will be a need to add an item to an order that has been placed

    public void deleteOrder(Long order_id, Long user_id);

    public List<Orders> getAllOrderHistory(Long admin_id) throws AccessDeniedException;

    public Orders markFavourite(Long order_id, Long user_id) throws ChangeSetPersister.NotFoundException;

    public List<Orders> viewPastOrdersByUser(Long user_id);
}