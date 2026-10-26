package com.centoro.api.repositories;

import com.centoro.api.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    Optional<Expense> findByExpenseId(String expenseId);
    List<Expense> findByProjectId(Long projectId);
    List<Expense> findByStatus(Expense.ExpenseStatus status);
    List<Expense> findByCategory(String category);
    List<Expense> findByVendorContainingIgnoreCase(String vendor);
    List<Expense> findByDateBetween(java.util.Date start, java.util.Date end);
}