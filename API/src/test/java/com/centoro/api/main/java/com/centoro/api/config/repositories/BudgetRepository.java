package com.centoro.api.repositories;

import com.centoro.api.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByBudgetId(String budgetId);
    Optional<Budget> findByCategory(String category);
}