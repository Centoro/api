package com.centoro.api.service;

import com.centoro.api.entities.Budget;
import com.centoro.api.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    
    @Autowired
    private BudgetRepository budgetRepository;
    
    @Transactional
    public Budget createBudget(Budget budget) {
        budget.setBudgetId("BDG-" + String.format("%04d", System.currentTimeMillis() % 10000));
        budget.setCreatedAt(new Date());
        budget.setUpdatedAt(new Date());
        if (budget.getSpent() == null) budget.setSpent(0.0);
        return budgetRepository.save(budget);
    }
    
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }
    
    public Optional<Budget> getBudgetById(Long id) {
        return budgetRepository.findById(id);
    }
    
    public Optional<Budget> getBudgetByCategory(String category) {
        return budgetRepository.findByCategory(category);
    }
    
    @Transactional
    public Budget updateBudget(Long id, Budget budget) {
        Budget existing = budgetRepository.findById(id).orElseThrow();
        existing.setCategory(budget.getCategory());
        existing.setAnnual(budget.getAnnual());
        existing.setSpent(budget.getSpent());
        existing.setUpdatedAt(new Date());
        return budgetRepository.save(existing);
    }
    
    @Transactional
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}