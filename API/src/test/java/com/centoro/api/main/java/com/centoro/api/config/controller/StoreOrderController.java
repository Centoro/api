package com.centoro.api.controller;

import com.centoro.api.entities.Client;
import com.centoro.api.entities.Contract;
import com.centoro.api.entities.Invoice;
import com.centoro.api.entities.Payment;
import com.centoro.api.repositories.InvoiceRepository;
import com.centoro.api.repositories.PaymentRepository;
import com.centoro.api.service.ClientService;
import com.centoro.api.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/store/orders")
@CrossOrigin(origins = "*")
public class StoreOrderController {
    private final ClientService clientService;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final ContractService contractService;

    public StoreOrderController(ClientService clientService, InvoiceRepository invoiceRepository,
                                PaymentRepository paymentRepository, ContractService contractService) {
        this.clientService = clientService;
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
        this.contractService = contractService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> request) {
        String name = text(request.get("name"));
        String email = text(request.get("email"));
        String phone = text(request.get("phone"));
        String item = text(request.get("item"));
        String license = text(request.get("license"));
        Double amount = number(request.get("amount"));
        if (name.isBlank() || email.isBlank() || item.isBlank() || amount == null || amount < 0) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Name, email, item and amount are required"));
        }

        Client client = clientService.getClientByEmail(email).orElseGet(() -> {
            Client newClient = new Client();
            newClient.setName(name);
            newClient.setEmail(email);
            newClient.setPhone(phone);
            newClient.setFirstName(name);
            return clientService.createClient(newClient);
        });

        Date now = new Date();
        Invoice invoice = new Invoice();
        invoice.setInvoiceId("WEB-INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        invoice.setTrackingNumber(invoice.getInvoiceId());
        invoice.setClientId(client.getId());
        invoice.setService("Website store - " + item);
        invoice.setItems("[{\"item\":\"" + escape(item) + "\",\"license\":\"" + escape(license) + "\"}]");
        invoice.setSubtotal(amount);
        invoice.setTotal(amount);
        invoice.setPaid(0.0);
        invoice.setStatus(Invoice.InvoiceStatus.unpaid);
        invoice.setIssueDate(now);
        invoice.setDueDate(now);
        invoice.setCreatedAt(now);
        invoice.setUpdatedAt(now);
        Invoice saved = invoiceRepository.save(invoice);

        return ResponseEntity.ok(Map.of("success", true, "status", "payment_pending", "data", Map.of(
            "invoiceId", saved.getId(), "invoiceNumber", saved.getInvoiceId(), "clientId", client.getId(), "amount", amount,
            "message", "Order recorded. Complete payment with the configured payment provider."
        )));
    }

    @PostMapping("/{invoiceId}/confirm")
    @Transactional
    public ResponseEntity<?> confirmOrder(@PathVariable Long invoiceId, @RequestBody Map<String, Object> request) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElse(null);
        if (invoice == null) return ResponseEntity.notFound().build();
        if (invoice.getStatus() == Invoice.InvoiceStatus.paid) return ResponseEntity.ok(Map.of("success", true, "message", "Order already confirmed"));

        Payment payment = new Payment();
        payment.setInvoiceId(invoice.getId());
        payment.setAmount(invoice.getTotal());
        payment.setMethod(text(request.getOrDefault("method", "online")));
        payment.setReference(text(request.getOrDefault("reference", "website-checkout")));
        payment.setSource("website");
        payment.setDate(new Date());
        payment.setPaymentId("WEB-PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        payment.setCreatedAt(new Date());
        paymentRepository.save(payment);

        invoice.setPaid(invoice.getTotal());
        invoice.setStatus(Invoice.InvoiceStatus.paid);
        invoice.setUpdatedAt(new Date());
        invoiceRepository.save(invoice);

        Contract contract = new Contract();
        contract.setName("Instrumental " + invoice.getService() + " - " + text(request.getOrDefault("license", "lease")));
        contract.setCategory("Music License");
        contract.setIssuedTo("{\"clientId\":" + invoice.getClientId() + ",\"invoiceId\":" + invoice.getId() + "}");
        contract.setUploadedAt(new Date());
        contract.setFileUrl("/api/store/orders/" + invoice.getId() + "/contract");
        Contract savedContract = contractService.createContract(contract);

        return ResponseEntity.ok(Map.of("success", true, "status", "paid", "data", Map.of(
            "invoiceId", invoice.getId(), "paymentId", payment.getId(), "contractId", savedContract.getId(), "contractUrl", savedContract.getFileUrl()
        )));
    }

    @GetMapping(value = "/{invoiceId}/contract", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> contract(@PathVariable Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElse(null);
        if (invoice == null || invoice.getStatus() != Invoice.InvoiceStatus.paid) return ResponseEntity.notFound().build();
        return ResponseEntity.ok("CENTORO ENTERPRISE MUSIC LICENSE\n\n" +
            "Invoice: " + invoice.getInvoiceId() + "\n" +
            "Item: " + invoice.getService() + "\n" +
            "Status: PAID\n\n" +
            "This license is issued to the customer recorded against the invoice.\n");
    }

    private static String text(Object value) { return value == null ? "" : String.valueOf(value).trim(); }
    private static Double number(Object value) { try { return value == null ? null : Double.valueOf(String.valueOf(value)); } catch (NumberFormatException e) { return null; } }
    private static String escape(String value) { return value.replace("\\", "\\\\").replace("\"", "\\\""); }
}
