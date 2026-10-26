package com.centoro.api.dto;

import java.util.Date;

public class BankTransactionDTO {
    private Long id;
    private String bankId;
    private Date date;
    private String description;
    private String type;
    private Double amount;
    private String reference;
    private Boolean reconciled;
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