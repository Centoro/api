package com.centoro.api.controller;

import com.centoro.api.entities.Payment;
import com.centoro.api.entities.Invoice;
import com.centoro.api.repositories.PaymentRepository;
import com.centoro.api.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentsController {

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping
    public ResponseEntity<?> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", payments));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment(@PathVariable Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", payment.get()));
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<?> getPaymentsByInvoice(@PathVariable String invoiceId) { // FIX: String
        List<Payment> payments = paymentRepository.findByInvoiceId(invoiceId);
        return ResponseEntity.ok(Map.of("success", true, "data", payments));
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
        if (payment.getInvoiceId() != null) {
            Optional<Invoice> invoiceOpt = invoiceRepository.findById(payment.getInvoiceId()); // FIX: String
            if (invoiceOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Invoice not found"));
            }
            Invoice invoice = invoiceOpt.get();
            invoice.setPaid(invoice.getPaid() + payment.getAmount());
            if (invoice.getPaid() >= invoice.getTotal()) invoice.setStatus(Invoice.InvoiceStatus.paid);
            else if (invoice.getPaid() > 0) invoice.setStatus(Invoice.InvoiceStatus.partial);
            invoice.setUpdatedAt(new Date());
            invoiceRepository.save(invoice);
        }
        payment.setPaymentId("PAY-" + String.format("%04d", System.currentTimeMillis() % 10000));
        payment.setCreatedAt(new Date());
        if (payment.getMethod() == null) payment.setMethod("EFT");
        if (payment.getSource() == null) payment.setSource("invoice");
        
        Payment saved = paymentRepository.save(payment);
        return ResponseEntity.ok(Map.of("success", true, "data", saved, "message", "Payment recorded successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        if (!paymentRepository.existsById(id)) return ResponseEntity.notFound().build();
        paymentRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Payment deleted successfully"));
    }
}
