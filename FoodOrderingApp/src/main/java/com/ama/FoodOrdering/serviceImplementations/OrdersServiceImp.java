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
    public OrderItem addOrderItem() {
        return null;
    }

    @Override
    public void deleteOrder() {

    }

    @Override
    public List<Orders> getOrderHistory() {
        return List.of();
    }

    @Override
    public OrderItem markFavourite() {
        return null;
    }

    @Override
    public int getTotalQuantityOrdered() {
        return 0;
    }

    @Override
    public Orders viewPastOrdersByUser(UUID user_id) {
        return null;
    }
}
