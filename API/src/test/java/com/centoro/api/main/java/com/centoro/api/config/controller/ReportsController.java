package com.centoro.api.controller;

import com.centoro.api.entities.Expense;
import com.centoro.api.entities.Invoice;
import com.centoro.api.entities.Project;
import com.centoro.api.repositories.InvoiceRepository;
import com.centoro.api.repositories.PaymentRepository;
import com.centoro.api.repositories.ProjectRepository;
import com.centoro.api.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportsController {

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/monthly-revenue")
    public ResponseEntity<?> getMonthlyRevenue() {
        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();
        
        // Get current year
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        
        // Initialize all months
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM yyyy");
        for (int m = 0; m < 12; m++) {
            cal.set(year, m, 1);
            String monthKey = monthFormat.format(cal.getTime());
            monthlyRevenue.put(monthKey, 0.0);
        }
        
        // Aggregate payments
        paymentRepository.findAll().forEach(payment -> {
            if (payment.getDate() != null) {
                cal.setTime(payment.getDate());
                if (cal.get(Calendar.YEAR) == year) {
                    String monthKey = monthFormat.format(payment.getDate());
                    monthlyRevenue.merge(monthKey, payment.getAmount(), Double::sum);
                }
            }
        });
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", monthlyRevenue
        ));
    }

    @GetMapping("/accounting-summary")
    public ResponseEntity<?> getAccountingSummary() {
        Double totalRevenue = paymentRepository.findAll().stream()
            .mapToDouble(p -> p.getAmount() != null ? p.getAmount() : 0.0)
            .sum();
        
        Double totalExpenses = expenseRepository.findAll().stream()
            .filter(e -> e.getStatus() == Expense.ExpenseStatus.approved)
            .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0.0)
            .sum();
        
        Double vatOutstanding = invoiceRepository.findAll().stream()
            .filter(i -> i.getStatus() != Invoice.InvoiceStatus.paid)
            .mapToDouble(i -> i.getVat() != null ? i.getVat() : 0.0)
            .sum();
        
        Double vatClaimable = expenseRepository.findAll().stream()
            .filter(e -> e.getStatus() == Expense.ExpenseStatus.approved)
            .mapToDouble(e -> e.getVatClaimable() != null ? e.getVatClaimable() : 0.0)
            .sum();
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalRevenue", totalRevenue);
        summary.put("totalExpenses", totalExpenses);
        summary.put("vatPayable", vatOutstanding - vatClaimable);
        summary.put("netProfit", totalRevenue - totalExpenses);
        summary.put("vatOutstanding", vatOutstanding);
        summary.put("vatClaimable", vatClaimable);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", summary
        ));
    }

    @GetMapping("/project-progress")
    public ResponseEntity<?> getProjectProgress() {
        List<Map<String, Object>> projectProgress = projectRepository.findAll().stream()
            .filter(p -> p.getDeletedAt() == null)
            .map(p -> {
                Map<String, Object> progress = new HashMap<>();
                progress.put("id", p.getId());
                progress.put("projectId", p.getProjectId());
                progress.put("name", p.getName());
                progress.put("progress", p.getProgress() != null ? p.getProgress() : 0);
                progress.put("status", p.getStatus() != null ? p.getStatus().toString() : "planning");
                progress.put("budget", p.getBudget() != null ? p.getBudget() : 0.0);
                progress.put("spent", p.getSpent() != null ? p.getSpent() : 0.0);
                progress.put("dueDate", p.getDueDate());
                progress.put("priority", p.getPriority());
                progress.put("service", p.getService());
                progress.put("clientId", p.getClientId());
                return progress;
            })
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", projectProgress
        ));
    }

    @GetMapping("/crm-summary")
    public ResponseEntity<?> getCrmSummary() {
        long totalClients = 0; // Would need ClientRepository
        long openComplaints = 0; // Would need ChatRepository
        long resolvedComplaints = 0; // Would need ChatRepository
        long completedProjects = projectRepository.findAll().stream()
            .filter(p -> p.getStatus() == Project.ProjectStatus.completed)
            .count();
        long activeProjects = projectRepository.findAll().stream()
            .filter(p -> p.getStatus() == Project.ProjectStatus.in_progress)
            .count();
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalClients", totalClients);
        summary.put("openComplaints", openComplaints);
        summary.put("resolvedComplaints", resolvedComplaints);
        summary.put("completedProjects", completedProjects);
        summary.put("activeProjects", activeProjects);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", summary
        ));
    }
}