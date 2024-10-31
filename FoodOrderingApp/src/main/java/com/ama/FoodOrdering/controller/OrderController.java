package com.ama.FoodOrdering.controller;

import com.ama.FoodOrdering.entities.OrderItem;
import com.ama.FoodOrdering.entities.Order;
import com.ama.FoodOrdering.responses.OrderResponse;
import com.ama.FoodOrdering.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/orders")

public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/placeOrder/{user_id}")
    public ResponseEntity<Order> placeOrder(@RequestBody List<OrderItem> orderItems,
                                            @PathVariable("user_id") Long user_id) {
        try {
            Order newOrder = orderService.placeOrder(orderItems, user_id);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{order_id}/{user_id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("order_id") Long order_id,
                                            @PathVariable("user_id") Long user_id) {
        orderService.deleteOrder(order_id, user_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/history/{admin_id}")
    public ResponseEntity<List<OrderResponse>> getAllOrderHistory(@PathVariable("admin_id") Long admin_id) throws AccessDeniedException {
        List<OrderResponse> allPastOrders = orderService.getAllOrderHistory(admin_id);
        return new ResponseEntity<>(allPastOrders, HttpStatus.OK);
    }

    @PatchMapping("/markAsFavourite/{order_id}/{user_id}")
    public ResponseEntity<Void> markFavourite(@PathVariable("order_id") Long order_id,
                                              @PathVariable("user_id") Long user_id) throws ChangeSetPersister.NotFoundException {
        orderService.markFavourite(order_id, user_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/myPastOrders/{user_id}")
    public ResponseEntity<List<OrderResponse>> viewPastOrdersByUser(@PathVariable("user_id") Long user_id) {
        List<OrderResponse> userPastOrders = orderService.viewPastOrdersByUser(user_id);
        return new ResponseEntity<>(userPastOrders, HttpStatus.OK);
    }
}