package com.ama.FoodOrdering.controller;

import com.ama.FoodOrdering.entities.Payments;
import com.ama.FoodOrdering.responses.PaymentResponse;
import com.ama.FoodOrdering.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/makePayment/{invoice_id}")
    // made the response on PostMan less cumbersome
    public ResponseEntity<PaymentResponse> makePayment(@PathVariable Long invoice_id) {
        try {
            Payments payment = paymentService.makePayment(invoice_id);
            PaymentResponse response = new PaymentResponse(payment.getPaymentIssueDate(), payment.getStatus());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/totalPaid/{user_id}")
    public ResponseEntity<Integer> getTotalPaid(@PathVariable Long user_id) {
        try {
            Integer totalPaid = paymentService.getTotalPaid(user_id);
            return new ResponseEntity<>(totalPaid, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/totalOwed/{user_id}")
    public ResponseEntity<Integer> getTotalOwed(@PathVariable Long user_id) {
        try {
            Integer totalOwed = paymentService.getTotalOwed(user_id);
            return new ResponseEntity<>(totalOwed, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/totalOverdue/{user_id}")
    public ResponseEntity<Integer> getTotalOverdue(@PathVariable Long user_id) {
        try {
            Integer totalOverdue = paymentService.getTotalOverdue(user_id);
            return new ResponseEntity<>(totalOverdue, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/accountSummary/{user_id}")
    public ResponseEntity<List<Payments>> getAccountSummary(@PathVariable Long user_id) {
        try {
            List<Payments> payments = paymentService.getAccountSummary(user_id);
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/monthlyBill/{user_id}")
    public ResponseEntity<List<Payments>> getMonthlyBill(@PathVariable Long user_id) {
        try {
            List<Payments> monthlyBill = paymentService.getMonthlyBillByUser(user_id);
            return new ResponseEntity<>(monthlyBill, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
