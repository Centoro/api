package com.centoro.api.controller;

import com.centoro.api.dto.BudgetDTO;
import com.centoro.api.entities.Budget;
import com.centoro.api.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "*")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping
    public ResponseEntity<?> getAllBudgets() {
        List<Budget> budgets = budgetService.getAllBudgets();
        List<BudgetDTO> budgetDTOs = budgets.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", budgetDTOs
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBudget(@PathVariable Long id) {
        Optional<Budget> budgetOpt = budgetService.getBudgetById(id);
        if (budgetOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(budgetOpt.get())
        ));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getBudgetByCategory(@PathVariable String category) {
        Optional<Budget> budgetOpt = budgetService.getBudgetByCategory(category);
        if (budgetOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(budgetOpt.get())
        ));
    }

    @PostMapping
    public ResponseEntity<?> createBudget(@RequestBody Budget budget) {
        Budget saved = budgetService.createBudget(budget);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Budget created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
        try {
            Budget updated = budgetService.updateBudget(id, budget);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Budget updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBudget(@PathVariable Long id) {
        if (!budgetService.getBudgetById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        budgetService.deleteBudget(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Budget deleted successfully"
        ));
    }

    private BudgetDTO convertToDTO(Budget budget) {
        BudgetDTO dto = new BudgetDTO();
        dto.setId(budget.getId());
        dto.setBudgetId(budget.getBudgetId());
        dto.setCategory(budget.getCategory());
        dto.setAnnual(budget.getAnnual());
        dto.setSpent(budget.getSpent() != null ? budget.getSpent() : 0.0);
        dto.setRemaining((budget.getAnnual() != null ? budget.getAnnual() : 0.0) - 
                         (budget.getSpent() != null ? budget.getSpent() : 0.0));
        dto.setCreatedAt(budget.getCreatedAt());
        dto.setUpdatedAt(budget.getUpdatedAt());
        return dto;
    }
}