package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bank_transactions")
public class BankTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "bank_id", unique = true, nullable = false, length = 50)
    private String bankId;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(nullable = false)
    private String description;
    
    @Column(length = 50)
    private String type = "deposit";
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double amount;
    
    @Column(length = 255)
    private String reference;
    
    @Column(name = "reconciled")
    private Boolean reconciled = false;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getBankId() { return bankId; }
    public void setBankId(String bankId) { this.bankId = bankId; }
    
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    
    public Boolean getReconciled() { return reconciled; }
    public void setReconciled(Boolean reconciled) { this.reconciled = reconciled; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
