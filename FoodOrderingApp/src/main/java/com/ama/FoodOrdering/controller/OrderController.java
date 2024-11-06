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

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody List<OrderItem> orderItems) {
        try {
            Order newOrder = orderService.placeOrder(orderItems);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteOrder(@RequestParam("id") Long order_id) {
        orderService.deleteOrder(order_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderResponse>> getAllOrderHistory() throws AccessDeniedException {
        List<OrderResponse> allPastOrders = orderService.getAllOrderHistory();
        return new ResponseEntity<>(allPastOrders, HttpStatus.OK);
    }

    @PatchMapping("/markAsFavourite")
    public ResponseEntity<Void> markFavourite(@RequestParam("id") Long order_id) throws ChangeSetPersister.NotFoundException {
        orderService.markFavourite(order_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/myPastOrders")
    public ResponseEntity<List<OrderResponse>> viewPastOrdersByUser() {
        List<OrderResponse> userPastOrders = orderService.viewPastOrdersByUser();
        return new ResponseEntity<>(userPastOrders, HttpStatus.OK);
    }
}