package com.ama.FoodOrdering.controller;

import com.ama.FoodOrdering.entities.OrderItem;
import com.ama.FoodOrdering.entities.Orders;
import com.ama.FoodOrdering.services.OrdersService;
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
    OrdersService ordersService;

    @PostMapping("/placeOrder/{user_id}")
    public ResponseEntity<Orders> placeOrder(@RequestBody List<OrderItem> orderItems,
                                             @PathVariable("user_id") Long user_id) {
        try {
            Orders newOrder = ordersService.placeOrder(orderItems, user_id);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{order_id}/{user_id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("order_id") Long order_id,
                                            @PathVariable("user_id") Long user_id) {
        ordersService.deleteOrder(order_id, user_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/history/{admin_id}")
    public ResponseEntity<List<Orders>> getAllOrderHistory(@PathVariable("admin_id") Long admin_id) throws AccessDeniedException {
        List<Orders> allPastOrders = ordersService.getAllOrderHistory(admin_id);
        return new ResponseEntity<List<Orders>>(allPastOrders, HttpStatus.OK);
    }

    @PatchMapping("/markFavourite/{order_id}/{user_id}")
    public ResponseEntity<Void> markFavourite(@PathVariable("order_id") Long order_id,
                                              @PathVariable("user_id") Long user_id) throws ChangeSetPersister.NotFoundException {
        ordersService.markFavourite(order_id, user_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/myPastOrders/{user_id}")
    public ResponseEntity<List<Orders>> viewPastOrdersByUser(@PathVariable("user_id") Long user_id) {
        List<Orders> userPastOrders = ordersService.viewPastOrdersByUser(user_id);
        return new ResponseEntity<List<Orders>>(userPastOrders, HttpStatus.OK);
    }
}