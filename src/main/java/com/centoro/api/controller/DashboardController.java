package com.centoro.api.controller;

import com.centoro.api.entities.Invoice;
import com.centoro.api.entities.Project;
import com.centoro.api.entities.Payment;
import com.centoro.api.repositories.InvoiceRepository;
import com.centoro.api.repositories.ProjectRepository;
import com.centoro.api.repositories.PaymentRepository;
import com.centoro.api.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private ChatRepository chatRepository;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        Double totalRevenue = paymentRepository.findAll().stream()
            .mapToDouble(Payment::getAmount)
            .sum();
        
        Double totalOutstanding = invoiceRepository.findAll().stream()
            .filter(i -> i.getStatus() != Invoice.InvoiceStatus.paid)
            .mapToDouble(i -> i.getTotal() - i.getPaid())
            .sum();
        
        long activeProjects = projectRepository.findAll().stream()
            .filter(p -> p.getStatus() != Project.ProjectStatus.completed)
            .filter(p -> p.getDeletedAt() == null)
            .count();
        
        long openChats = chatRepository.findByStatus("open").size();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRevenue", totalRevenue);
        stats.put("totalOutstanding", totalOutstanding);
        stats.put("activeProjects", activeProjects);
        stats.put("openChats", openChats);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", stats
        ));
    }
}