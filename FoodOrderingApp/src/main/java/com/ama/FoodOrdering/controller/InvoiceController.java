package com.ama.FoodOrdering.controller;

import com.ama.FoodOrdering.entities.Invoice;
import com.ama.FoodOrdering.entities.MenuItem;
<<<<<<< HEAD
import com.ama.FoodOrdering.responses.InvoiceResponse;
=======
>>>>>>> c7b5231f2332803222fff22b13d11de207a41d38
import com.ama.FoodOrdering.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
<<<<<<< HEAD
import java.util.stream.Collectors;
=======
>>>>>>> c7b5231f2332803222fff22b13d11de207a41d38

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/generate/{order_id}/{user_id}")
<<<<<<< HEAD
    public ResponseEntity<InvoiceResponse> generateInvoice(@PathVariable("order_id") Long order_id,
                                                           @PathVariable("user_id") Long user_id) throws NotFoundException {
        Invoice invoice = invoiceService.generateInvoice(order_id, user_id);

        InvoiceResponse response = new InvoiceResponse(
                invoice.getId(),
                invoice.getTotalAmount(),
                invoice.getStatus(),
                invoice.getUser().getName(),
                invoice.getOrder().getId()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/showInvoice/{order_id}/{user_id}")
    public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable("order_id") Long order_id,
                                              @PathVariable("user_id") Long user_id) throws NotFoundException {
        Invoice invoice = invoiceService.getInvoice(order_id, user_id);

        InvoiceResponse response = new InvoiceResponse(
                invoice.getId(),
                invoice.getTotalAmount(),
                invoice.getStatus(),
                invoice.getUser().getName(),
                invoice.getOrder().getId()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/showAll/{user_id}")
    public ResponseEntity<Set<InvoiceResponse>> getAllInvoice(@PathVariable("user_id") Long user_id) throws NotFoundException {
        Set<Invoice> allInvoice = invoiceService.getAllInvoice(user_id);

        Set<InvoiceResponse> responseSet = allInvoice.stream()
                .map(invoice -> new InvoiceResponse(
                        invoice.getId(),
                        invoice.getTotalAmount(),
                        invoice.getStatus(),
                        invoice.getUser().getName(),
                        invoice.getOrder().getId()
                ))
                .collect(Collectors.toSet());
        return new ResponseEntity<>(responseSet, HttpStatus.OK);
=======
    public ResponseEntity<Invoice> generateInvoice(@PathVariable("order_id") Long order_id,
                                                   @PathVariable("user_id") Long user_id) throws NotFoundException {
        Invoice invoice = invoiceService.generateInvoice(order_id, user_id);
        return new ResponseEntity<>(invoice, HttpStatus.CREATED);
    }

    @GetMapping("/showInvoice/{order_id}/{user_id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable("order_id") Long order_id,
                                              @PathVariable("user_id") Long user_id) throws NotFoundException {
        Invoice invoice = invoiceService.getInvoice(order_id, user_id);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @GetMapping("/showAll/{user_id}")
    public ResponseEntity<Set<Invoice>> getAllInvoice(@PathVariable("user_id") Long user_id) throws NotFoundException {
        Set<Invoice> allInvoice = invoiceService.getAllInvoice(user_id);
        return new ResponseEntity<>(allInvoice, HttpStatus.OK);
>>>>>>> c7b5231f2332803222fff22b13d11de207a41d38
    }
}
