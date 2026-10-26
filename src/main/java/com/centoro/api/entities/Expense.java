package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "expenses")
public class Expense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "expense_id", unique = true, nullable = false, length = 50)
    private String expenseId;
    
    @Column(nullable = false)
    private String vendor;
    
    @Column(length = 255)
    private String description;
    
    @Column(length = 100)
    private String category;
    
    @Column(name = "project_id")
    private Long projectId;
    
    @Column(nullable = false, columnDefinition = "DECIMAL(18,2)")
    private Double amount;
    
    @Column(name = "vat_claimable", columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double vatClaimable = 0.0;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ExpenseStatus status = ExpenseStatus.pending;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    
    public enum ExpenseStatus {
        pending, approved
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getExpenseId() { return expenseId; }
    public void setExpenseId(String expenseId) { this.expenseId = expenseId; }
    public String getVendor() { return vendor; }
    public void setVendor(String vendor) { this.vendor = vendor; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public Double getVatClaimable() { return vatClaimable; }
    public void setVatClaimable(Double vatClaimable) { this.vatClaimable = vatClaimable; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public ExpenseStatus getStatus() { return status; }
    public void setStatus(ExpenseStatus status) { this.status = status; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}