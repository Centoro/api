package com.centoro.api.dto;

import java.util.Date;

public class BudgetDTO {
    private Long id;
    private String budgetId;
    private String category;
    private Double annual;
    private Double spent;
    private Double remaining;
    private Date createdAt;
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
    public Double getRemaining() { return remaining; }
    public void setRemaining(Double remaining) { this.remaining = remaining; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}