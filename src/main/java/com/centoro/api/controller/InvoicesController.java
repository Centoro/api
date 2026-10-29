package com.centoro.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.centoro.api.entities.Client;
import com.centoro.api.entities.Invoice;
import com.centoro.api.repositories.InvoiceRepository;
import com.centoro.api.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoicesController {

    @Autowired private InvoiceRepository invoiceRepository;
    @Autowired private ClientRepository clientRepository;
    
    @GetMapping
    public ResponseEntity<?> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        invoices.removeIf(i -> i.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of("success", true, "data", invoices));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoice(@PathVariable String id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty() || invoice.get().getDeletedAt() != null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", invoice.get()));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getInvoicesByClient(@PathVariable String clientId) {
        List<Invoice> invoices = invoiceRepository.findByClientId(clientId);
        return ResponseEntity.ok(Map.of("success", true, "data", invoices));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getInvoicesByStatus(@PathVariable String status) {
        Invoice.InvoiceStatus invoiceStatus;
        try { invoiceStatus = Invoice.InvoiceStatus.valueOf(status); } 
        catch (IllegalArgumentException e) { return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Invalid status value")); }
        List<Invoice> invoices = invoiceRepository.findByStatus(invoiceStatus);
        return ResponseEntity.ok(Map.of("success", true, "data", invoices));
    }

    @GetMapping("/overdue")
    public ResponseEntity<?> getOverdueInvoices() {
        List<Invoice> invoices = invoiceRepository.findOverdueInvoices();
        return ResponseEntity.ok(Map.of("success", true, "data", invoices));
    }

    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice) {
        if (!clientRepository.existsById(invoice.getClientId())) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Client not found"));
        }
        invoice.setInvoiceId("INV-" + String.format("%04d", System.currentTimeMillis() % 10000));
        invoice.setCreatedAt(new Date());
        invoice.setUpdatedAt(new Date());
        invoice.setPaid(0.0);
        if (invoice.getStatus() == null) invoice.setStatus(Invoice.InvoiceStatus.unpaid);
        if (invoice.getSubtotal() == null) invoice.setSubtotal(0.0);
        if (invoice.getDiscountPct() == null) invoice.setDiscountPct(0.0);
        if (invoice.getVat() == null) invoice.setVat(0.0);
        if (invoice.getTotal() == null) invoice.setTotal(0.0);
        Invoice saved = invoiceRepository.save(invoice);
        return ResponseEntity.ok(Map.of("success", true, "data", saved, "message", "Invoice created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable String id, @RequestBody Invoice invoice) {
        Optional<Invoice> existingOpt = invoiceRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) return ResponseEntity.notFound().build();
        Invoice existing = existingOpt.get();
        existing.setClientId(invoice.getClientId());
        existing.setProjectId(invoice.getProjectId());
        existing.setService(invoice.getService());
        existing.setItems(invoice.getItems());
        existing.setSubtotal(invoice.getSubtotal());
        existing.setDiscountPct(invoice.getDiscountPct());
        existing.setVat(invoice.getVat());
        existing.setTotal(invoice.getTotal());
        existing.setStatus(invoice.getStatus());
        existing.setIssueDate(invoice.getIssueDate());
        existing.setDueDate(invoice.getDueDate());
        existing.setUpdatedAt(new Date());
        Invoice updated = invoiceRepository.save(existing);
        return ResponseEntity.ok(Map.of("success", true, "data", updated, "message", "Invoice updated successfully"));
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<?> recordPayment(@PathVariable String id, @RequestBody Map<String, Object> paymentData) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(id);
        if (invoiceOpt.isEmpty() || invoiceOpt.get().getDeletedAt() != null) return ResponseEntity.notFound().build();
        Invoice invoice = invoiceOpt.get();
        Double amount = Double.valueOf(paymentData.get("amount").toString());
        invoice.setPaid(invoice.getPaid() + amount);
        if (invoice.getPaid() >= invoice.getTotal()) invoice.setStatus(Invoice.InvoiceStatus.paid);
        else if (invoice.getPaid() > 0) invoice.setStatus(Invoice.InvoiceStatus.partial);
        invoice.setUpdatedAt(new Date());
        Invoice updated = invoiceRepository.save(invoice);
        return ResponseEntity.ok(Map.of("success", true, "data", updated, "message", "Payment recorded successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable String id) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(id);
        if (invoiceOpt.isEmpty()) return ResponseEntity.notFound().build();
        Invoice invoice = invoiceOpt.get();
        invoice.setDeletedAt(new Date());
        invoiceRepository.save(invoice);
        return ResponseEntity.ok(Map.of("success", true, "message", "Invoice deleted successfully"));
    }
    
    @GetMapping("/stats")
    public ResponseEntity<?> getInvoiceStats() {
        List<Invoice> invoices = invoiceRepository.findAll();
        invoices.removeIf(i -> i.getDeletedAt() != null);
        double totalBilled = invoices.stream().mapToDouble(i -> i.getTotal() != null ? i.getTotal() : 0.0).sum();
        double totalPaid = invoices.stream().mapToDouble(i -> i.getPaid() != null ? i.getPaid() : 0.0).sum();
        Map<String, Object> stats = Map.of("totalBilled", totalBilled, "totalPaid", totalPaid, "totalOutstanding", totalBilled - totalPaid, "totalInvoices", (long) invoices.size());
        return ResponseEntity.ok(Map.of("success", true, "data", stats));
    }

    @GetMapping("/client/{clientId}/stats")
    public ResponseEntity<?> getClientInvoiceStats(@PathVariable String clientId) {
        List<Invoice> invoices = invoiceRepository.findByClientId(clientId);
        invoices.removeIf(i -> i.getDeletedAt() != null);
        double totalBilled = invoices.stream().mapToDouble(i -> i.getTotal() != null ? i.getTotal() : 0.0).sum();
        double totalPaid = invoices.stream().mapToDouble(i -> i.getPaid() != null ? i.getPaid() : 0.0).sum();
        long paidCount = invoices.stream().filter(i -> i.getStatus() == Invoice.InvoiceStatus.paid).count();
        Map<String, Object> stats = Map.of("totalBilled", totalBilled, "totalPaid", totalPaid, "totalOutstanding", totalBilled - totalPaid, "totalInvoices", (long) invoices.size(), "paidCount", paidCount, "unpaidCount", invoices.size() - paidCount);
        return ResponseEntity.ok(Map.of("success", true, "data", stats));
    }

    @GetMapping("/services")
    public ResponseEntity<?> getInvoiceServices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        invoices.removeIf(i -> i.getDeletedAt() != null);
        Set<String> services = new HashSet<>();
        for (Invoice invoice : invoices) {
            if (invoice.getService() != null && !invoice.getService().isEmpty()) services.add(invoice.getService());
        }
        return ResponseEntity.ok(Map.of("success", true, "data", new ArrayList<>(services)));
    }

    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<?> getInvoiceByTrackingNumber(@PathVariable String trackingNumber) {
        Optional<Invoice> invoice = invoiceRepository.findByTrackingNumber(trackingNumber);
        if (invoice.isEmpty() || invoice.get().getDeletedAt() != null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", invoice.get()));
    }

    @PostMapping("/{id}/mark-paid")
    public ResponseEntity<?> markInvoiceAsPaid(@PathVariable String id) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(id);
        if (invoiceOpt.isEmpty() || invoiceOpt.get().getDeletedAt() != null) return ResponseEntity.notFound().build();
        Invoice invoice = invoiceOpt.get();
        invoice.setPaid(invoice.getTotal());
        invoice.setStatus(Invoice.InvoiceStatus.paid);
        invoice.setUpdatedAt(new Date());
        Invoice saved = invoiceRepository.save(invoice);
        return ResponseEntity.ok(Map.of("success", true, "data", saved, "message", "Invoice marked as paid"));
    }

    @PostMapping("/{id}/generate-tracking")
    public ResponseEntity<?> generateTrackingNumber(@PathVariable String id) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(id);
        if (invoiceOpt.isEmpty() || invoiceOpt.get().getDeletedAt() != null) return ResponseEntity.notFound().build();
        Invoice invoice = invoiceOpt.get();
        String trackingNumber = generateTrackingNumber(invoice.getClientId(), new Date());
        invoice.setTrackingNumber(trackingNumber);
        invoice.setUpdatedAt(new Date());
        invoiceRepository.save(invoice);
        return ResponseEntity.ok(Map.of("success", true, "data", Map.of("trackingNumber", trackingNumber), "message", "Tracking number generated"));
    }

    private String generateTrackingNumber(String clientId, Date issueDate) {
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        String code = clientOpt.map(c -> c.getCode() != null ? c.getCode() : "CL").orElse("CL");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = sdf.format(issueDate);
        int count = invoiceRepository.findAll().size() + 1;
        return code + String.format("%02d", count) + "-" + dateStr;
    }
}
