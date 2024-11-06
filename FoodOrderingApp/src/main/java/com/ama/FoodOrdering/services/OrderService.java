package com.ama.FoodOrdering.services;
import com.ama.FoodOrdering.entities.OrderItem;
import com.ama.FoodOrdering.entities.Order;
import com.ama.FoodOrdering.responses.OrderResponse;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface OrderService {
    public Order placeOrder(List<OrderItem> orderItems) throws ChangeSetPersister.NotFoundException;

    public void deleteOrder(Long order_id);

    public List<OrderResponse> getAllOrderHistory() throws AccessDeniedException;

    public Order markFavourite(Long order_id) throws ChangeSetPersister.NotFoundException;

    public List<OrderResponse> viewPastOrdersByUser();
}