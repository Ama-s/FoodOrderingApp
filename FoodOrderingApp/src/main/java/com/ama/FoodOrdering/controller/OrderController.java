package com.ama.FoodOrdering.controller;

import com.ama.FoodOrdering.entities.OrderItem;
import com.ama.FoodOrdering.entities.Orders;
import com.ama.FoodOrdering.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
