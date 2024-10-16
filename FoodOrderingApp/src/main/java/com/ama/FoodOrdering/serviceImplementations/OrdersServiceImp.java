package com.ama.FoodOrdering.serviceImplementations;

import com.ama.FoodOrdering.entities.*;
import com.ama.FoodOrdering.enums.MenuStatus;
import com.ama.FoodOrdering.enums.OrderStatus;
import com.ama.FoodOrdering.repos.InvoiceRepository;
import com.ama.FoodOrdering.repos.MenuItemRepository;
import com.ama.FoodOrdering.repos.OrdersRepository;
import com.ama.FoodOrdering.repos.UsersRepository;
import com.ama.FoodOrdering.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersServiceImp implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    private InvoiceRepository invoiceRepository;

    @Override
    public Orders placeOrder(List<OrderItem> orderItems, Long user_id) throws ChangeSetPersister.NotFoundException {
        Orders newOrder = new Orders();
        Users user = usersRepository.findById(user_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        for (OrderItem orderItem : orderItems) {
            MenuItem menuItem = menuItemRepository.findById(orderItem.getMenuItem().getId())
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

            if(menuItem.getStatus() == MenuStatus.OUT_OF_STOCK) {
                throw new IllegalArgumentException("MenuItem is out of stock");
            }
            orderItem.setMenuItem(menuItem);
            orderItem.setOrder(newOrder);
        }

        newOrder.setOrderItems(orderItems);
        newOrder.setUser(user);
        newOrder.setCreatedBy(user_id);

        newOrder.setOrderDate(LocalDate.now());
        newOrder.setDueDate(LocalDate.now().plusDays(30));
        newOrder.setStatus(OrderStatus.RECEIVED);
        // this is where I'm supposed to save the generated invoice to the order

        return ordersRepository.save(newOrder);
    }

    @Override
    public void deleteOrder(Long order_id, Long user_id) {
        try {
            Users user = usersRepository.findById(user_id)
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

            Orders order = ordersRepository.findById(order_id)
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

            if (order.getStatus() == OrderStatus.RECEIVED) {
                ordersRepository.deleteById(order_id);
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
    public List<Orders> getAllOrderHistory(Long admin_id) throws AccessDeniedException {
        Users user = usersRepository.findById(admin_id).orElseThrow();
        if (!"admin".equals(user.getRole())) {
            throw new AccessDeniedException("User is not authorized to perform this action");
        }

        return ordersRepository.findAll();
    }

    @Override
    public Orders markFavourite(Long order_id, Long user_id) throws ChangeSetPersister.NotFoundException {
        Users user = usersRepository.findById(user_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        Orders favouriteOrder = ordersRepository.findById(order_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        favouriteOrder.setIsFavourite(true);

        return ordersRepository.save(favouriteOrder);
    }

    @Override
    public List<Orders> viewPastOrdersByUser(Long user_id) {
        Users user = usersRepository.findById(user_id).orElseThrow();
        List<Orders> pastOrdersByUser = user.getOrders();
        return pastOrdersByUser;
    }
}
