package com.ama.FoodOrdering.serviceImplementations;

import com.ama.FoodOrdering.entities.Invoice;
import com.ama.FoodOrdering.entities.Payments;
import com.ama.FoodOrdering.entities.Users;
import com.ama.FoodOrdering.enums.InvoiceStatus;
import com.ama.FoodOrdering.enums.PaymentStatus;
import com.ama.FoodOrdering.repos.InvoiceRepository;
import com.ama.FoodOrdering.repos.PaymentRepository;
import com.ama.FoodOrdering.repos.UsersRepository;
import com.ama.FoodOrdering.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class PaymentServiceImp implements PaymentService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payments makePayment(Long invoice_id) throws ChangeSetPersister.NotFoundException {
        Invoice invoice = invoiceRepository.findById(invoice_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        Payments payment = new Payments();

        invoice.setStatus(InvoiceStatus.PAID);
        payment.setStatus(PaymentStatus.SUCCESSFUL);
        payment.setInvoice(invoice);
        payment.setPaymentIssueDate(LocalDate.now());
        return paymentRepository.save(payment);
    }

    @Override
    public Integer getTotalPaid(Long user_id) throws ChangeSetPersister.NotFoundException {
        Users user = usersRepository.findById(user_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        Set<Invoice> userInvoices = user.getInvoices();

        Integer totalPaid = 0;
        for (Invoice invoice : userInvoices) {
            if (invoice.getStatus() == InvoiceStatus.PAID && LocalDate.now().isBefore(invoice.getOrder().getDueDate())) {
                totalPaid += invoice.getTotalAmount();
            }
        }
        return totalPaid;
    }

    @Override
    public Integer getTotalOwed(Long user_id) throws ChangeSetPersister.NotFoundException {
        Users user = usersRepository.findById(user_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        Set<Invoice> userInvoices = user.getInvoices();

        Integer totalOwed = 0;
        for (Invoice invoice : userInvoices) {
            if (invoice.getStatus() == InvoiceStatus.ISSUED && LocalDate.now().isBefore(invoice.getOrder().getDueDate())) {
                totalOwed += invoice.getTotalAmount();
            }
        }
        return totalOwed;
    }

    @Override
    public Integer getTotalOverdue(Long user_id) throws ChangeSetPersister.NotFoundException {
        Users user = usersRepository.findById(user_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        Set<Invoice> userInvoices = user.getInvoices();

        Integer totalOverdue = 0;
        for (Invoice invoice : userInvoices) {
            if (invoice.getStatus() == InvoiceStatus.ISSUED && LocalDate.now().isAfter(invoice.getOrder().getDueDate())) {
                totalOverdue += invoice.getTotalAmount();
            }
        }
        return totalOverdue;
    }

    @Override
    public List<Payments> getAccountSummary(Long user_id) throws ChangeSetPersister.NotFoundException {
        Users user = usersRepository.findById(user_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return paymentRepository.findByUser(user);
    }

    @Override
    public List<Payments> getMonthlyBillByUser(Long user_id) throws ChangeSetPersister.NotFoundException {
        Users user = usersRepository.findById(user_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Assuming you have a method to filter payments within a specific month
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        return paymentRepository.findByUserAndDateBetween(user, startOfMonth, endOfMonth);
    }

}
