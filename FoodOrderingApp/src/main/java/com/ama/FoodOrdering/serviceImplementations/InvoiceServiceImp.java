package com.ama.FoodOrdering.serviceImplementations;

import com.ama.FoodOrdering.entities.Invoice;
import com.ama.FoodOrdering.entities.OrderItem;
import com.ama.FoodOrdering.entities.Orders;
import com.ama.FoodOrdering.entities.Users;
import com.ama.FoodOrdering.enums.InvoiceStatus;
import com.ama.FoodOrdering.repos.InvoiceRepository;
import com.ama.FoodOrdering.repos.OrdersRepository;
import com.ama.FoodOrdering.repos.UsersRepository;
import com.ama.FoodOrdering.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class InvoiceServiceImp implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Invoice generateInvoice(Long order_id, Long user_id) throws ChangeSetPersister.NotFoundException {
        Users user = usersRepository.findById(user_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        Orders order = ordersRepository.findById(order_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        Invoice invoice = new Invoice();
        invoice.setStatus(InvoiceStatus.DRAFT);
        invoice.setUser(user);
        invoice.setOrder(order);

        List<OrderItem> orderItemList = order.getOrderItems();
        Integer total_amount = 0;
        for (OrderItem orderItem : orderItemList) {
            Integer price = orderItem.getMenuItem().getPrice();
            Short quantity = orderItem.getQuantity();
            Integer total = price * quantity;
            total_amount += total;
        }
        invoice.setTotalAmount(total_amount);
        invoice.setStatus(InvoiceStatus.ISSUED);
        invoice.setCreatedBy(user_id);

        order.setInvoice(invoice);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getInvoice(Long order_id, Long user_id) throws ChangeSetPersister.NotFoundException {
        // Find the user by ID
        Users user = usersRepository.findById(user_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Find the order by ID
        Orders order = ordersRepository.findById(order_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Find the invoice by order and user
        return invoiceRepository.findByOrderAndUser(order, user)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }


    @Override
    public Set<Invoice> getAllInvoice(Long user_id) throws ChangeSetPersister.NotFoundException {
        Users user = usersRepository.findById(user_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return invoiceRepository.findByUser(user);
    }
}
