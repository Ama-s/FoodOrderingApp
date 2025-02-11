package com.ama.FoodOrdering.serviceImplementations;

import com.ama.FoodOrdering.entities.*;
import com.ama.FoodOrdering.enums.MenuStatus;
import com.ama.FoodOrdering.enums.OrderStatus;
import com.ama.FoodOrdering.enums.UserRole;
import com.ama.FoodOrdering.repos.InvoiceRepository;
import com.ama.FoodOrdering.repos.MenuItemRepository;
import com.ama.FoodOrdering.repos.OrderRepository;
import com.ama.FoodOrdering.repos.UserRepository;
import com.ama.FoodOrdering.dto.OrderResponse;
import com.ama.FoodOrdering.services.AuthService;
import com.ama.FoodOrdering.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    private InvoiceRepository invoiceRepository;

    @Autowired
    private AuthService authService;

    @Override
    public OrderResponse placeOrder(List<OrderItem> orderItems) throws ChangeSetPersister.NotFoundException {
        Order newOrder = new Order();
        User user = userRepository.findById(authService.getCurrentUserId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        for (OrderItem orderItem : orderItems) {
            MenuItem menuItem = menuItemRepository.findById(orderItem.getMenuItem().getId())
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);

            if (menuItem.getStatus() == MenuStatus.OUT_OF_STOCK) {
                throw new IllegalArgumentException("MenuItem is out of stock");
            }
            orderItem.setMenuItem(menuItem);
            orderItem.setOrder(newOrder);
        }

        newOrder.setOrderItems(orderItems);
        newOrder.setUser(user);
        newOrder.setCreatedBy(authService.getCurrentUserId());
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setDueDate(LocalDate.now().plusDays(30));
        newOrder.setStatus(OrderStatus.RECEIVED);
        newOrder.setIsFavourite(false);

        // Save the order
        Order savedOrder = orderRepository.save(newOrder);

        // Map the saved Order entity to OrderResponse DTO
        return mapToOrderResponse(savedOrder);
    }



    @Override
    public void deleteOrder(Long order_id) {
        try {
            User user = userRepository.findById(authService.getCurrentUserId())

                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

            Order order = orderRepository.findById(order_id)
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

            if (order.getStatus() == OrderStatus.RECEIVED) {
                orderRepository.deleteById(order_id);
                System.out.println("Order deleted successfully.");
            } else {
                System.out.println("Your order has already been confirmed or processed, it cannot be deleted.");
            }
        } catch (ChangeSetPersister.NotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while trying to delete the order: " + e.getMessage());
        }
    }

    @Override
    public List<OrderResponse> getAllOrderHistory() throws AccessDeniedException {
        User user = userRepository.findById(authService.getCurrentUserId()).orElseThrow();
        if (user.getRole() != UserRole.ADMIN) {
            throw new AccessDeniedException("User is not authorized to perform this action");
        }

        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(order -> {
            List<String> itemNames = order.getOrderItems().stream()
                    .map(orderItem -> orderItem.getMenuItem().getName())
                    .collect(Collectors.toList());
            List<Integer> itemPrices = order.getOrderItems().stream()
                    .map(orderItem -> orderItem.getMenuItem().getPrice())
                    .collect(Collectors.toList());
            List<Short> quantities = order.getOrderItems().stream()
                    .map(OrderItem::getQuantity)
                    .collect(Collectors.toList());

            return new OrderResponse(
                    order.getId(),
                    order.getUser().getName(),
                    itemNames,
                    itemPrices,
                    quantities,
                    order.getOrderDate(),
                    order.getDueDate(),
                    order.getStatus(),
                    order.getIsFavourite()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public Order markFavourite(Long order_id) throws ChangeSetPersister.NotFoundException {

        Order favouriteOrder = orderRepository.findById(order_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        favouriteOrder.setIsFavourite(true);

        return orderRepository.save(favouriteOrder);
    }

    @Override
    public List<OrderResponse> viewPastOrdersByUser() {
        User user = userRepository.findById(authService.getCurrentUserId()).orElseThrow();
        List<Order> pastOrdersByUser = user.getOrders();

        // Map Orders to OrderResponse
        return pastOrdersByUser.stream().map(order -> {
            List<String> itemNames = order.getOrderItems().stream()
                    .map(orderItem -> orderItem.getMenuItem().getName())
                    .collect(Collectors.toList());
            List<Integer> itemPrices = order.getOrderItems().stream()
                    .map(orderItem -> orderItem.getMenuItem().getPrice())
                    .collect(Collectors.toList());
            List<Short> quantities = order.getOrderItems().stream()
                    .map(OrderItem::getQuantity)
                    .collect(Collectors.toList());

            return new OrderResponse(
                    order.getId(),
                    order.getUser().getName(),
                    itemNames,    // List of item names
                    itemPrices,   // List of item prices
                    quantities,   // List of quantities
                    order.getOrderDate(),
                    order.getDueDate(),
                    order.getStatus(),
                    order.getIsFavourite()
            );
        }).collect(Collectors.toList());
    }

    private OrderResponse mapToOrderResponse(Order order) {
        List<String> names = order.getOrderItems().stream()
                .map(orderItem -> orderItem.getMenuItem().getName())
                .toList();

        List<Integer> prices = order.getOrderItems().stream()
                .map(orderItem -> orderItem.getMenuItem().getPrice())
                .toList();

        List<Short> quantities = order.getOrderItems().stream()
                .map(OrderItem::getQuantity)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getUser().getName(), // Assuming User has a getUsername() method
                names,
                prices,
                quantities,
                order.getOrderDate(),
                order.getDueDate(),
                order.getStatus(),
                order.getIsFavourite()
        );
    }


}
