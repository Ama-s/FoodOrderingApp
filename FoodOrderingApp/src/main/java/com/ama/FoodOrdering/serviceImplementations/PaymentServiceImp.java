package com.ama.FoodOrdering.serviceImplementations;

import com.ama.FoodOrdering.entities.Invoice;
import com.ama.FoodOrdering.entities.Payment;
import com.ama.FoodOrdering.entities.User;
import com.ama.FoodOrdering.enums.InvoiceStatus;
import com.ama.FoodOrdering.enums.PaymentStatus;
import com.ama.FoodOrdering.repos.InvoiceRepository;
import com.ama.FoodOrdering.repos.PaymentRepository;
import com.ama.FoodOrdering.repos.UserRepository;
import com.ama.FoodOrdering.responses.PaymentResponse;
import com.ama.FoodOrdering.services.AuthService;
import com.ama.FoodOrdering.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImp implements PaymentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AuthService authService;

    @Override
    public Payment makePayment(Long invoice_id) throws ChangeSetPersister.NotFoundException {
        Invoice invoice = invoiceRepository.findById(invoice_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        Payment payment = new Payment();

        invoice.setStatus(InvoiceStatus.PAID);
        payment.setStatus(PaymentStatus.SUCCESSFUL);
        payment.setInvoice(invoice);
        payment.setPaymentIssueDate(LocalDate.now());
        return paymentRepository.save(payment);
    }

    @Override
    public Integer getTotalPaid() throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(authService.getCurrentUserId()).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
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
    public Integer getTotalOwed() throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(authService.getCurrentUserId()).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
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
    public Integer getTotalOverdue() throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(authService.getCurrentUserId())

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
    public List<PaymentResponse> getAccountSummary() throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(authService.getCurrentUserId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        List<Payment> payments = paymentRepository.findByUser(user);

        if (payments.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }

        return payments.stream().map(payment ->
            new PaymentResponse(payment.getId(),
                    payment.getInvoice().getId(),
                    payment.getPaymentIssueDate(),
                    payment.getStatus())
        ).collect(Collectors.toList());
    }
}
