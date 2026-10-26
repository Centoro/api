package com.centoro.api.controller;

import com.centoro.api.entities.Expense;
import com.centoro.api.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpensesController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping
    public ResponseEntity<?> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        expenses.removeIf(e -> e.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", expenses
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpense(@PathVariable Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isEmpty() || expense.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", expense.get()
        ));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getExpensesByProject(@PathVariable Long projectId) {
        List<Expense> expenses = expenseRepository.findByProjectId(projectId);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", expenses
        ));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getExpensesByStatus(@PathVariable String status) {
        Expense.ExpenseStatus expenseStatus;
        try {
            expenseStatus = Expense.ExpenseStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Invalid status value"
            ));
        }
        List<Expense> expenses = expenseRepository.findByStatus(expenseStatus);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", expenses
        ));
    }

    @PostMapping
    public ResponseEntity<?> createExpense(@RequestBody Expense expense) {
        // Generate expense ID
        expense.setExpenseId("EXP-" + String.format("%04d", System.currentTimeMillis() % 10000));
        expense.setCreatedAt(new Date());
        expense.setUpdatedAt(new Date());
        
        if (expense.getStatus() == null) expense.setStatus(Expense.ExpenseStatus.pending);
        if (expense.getVatClaimable() == null) expense.setVatClaimable(0.0);
        
        Expense saved = expenseRepository.save(expense);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Expense created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        Optional<Expense> existingOpt = expenseRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        Expense existing = existingOpt.get();
        existing.setVendor(expense.getVendor());
        existing.setDescription(expense.getDescription());
        existing.setCategory(expense.getCategory());
        existing.setProjectId(expense.getProjectId());
        existing.setAmount(expense.getAmount());
        existing.setVatClaimable(expense.getVatClaimable());
        existing.setDate(expense.getDate());
        existing.setStatus(expense.getStatus());
        existing.setUpdatedAt(new Date());
        
        Expense updated = expenseRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Expense updated successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        Optional<Expense> expenseOpt = expenseRepository.findById(id);
        if (expenseOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Expense expense = expenseOpt.get();
        expense.setDeletedAt(new Date());
        expenseRepository.save(expense);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Expense deleted successfully"
        ));
    }
}