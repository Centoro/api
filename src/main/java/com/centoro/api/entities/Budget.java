package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "budget_id", unique = true, nullable = false, length = 50)
    private String budgetId;
    
    @Column(nullable = false)
    private String category;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double annual = 0.0;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double spent = 0.0;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBudgetId() { return budgetId; }
    public void setBudgetId(String budgetId) { this.budgetId = budgetId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Double getAnnual() { return annual; }
    public void setAnnual(Double annual) { this.annual = annual; }
    public Double getSpent() { return spent; }
    public void setSpent(Double spent) { this.spent = spent; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}