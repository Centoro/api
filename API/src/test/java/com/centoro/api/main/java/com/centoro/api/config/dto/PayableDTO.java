package com.centoro.api.dto;

import java.util.Date;

public class PayableDTO {
    private Long id;
    private String payableId;
    private String supplier;
    private String description;
    private Double amount;
    private Double vat;
    private Date dueDate;
    private String status;
    private Date paidDate;
    private Date createdAt;
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPayableId() { return payableId; }
    public void setPayableId(String payableId) { this.payableId = payableId; }
    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public Double getVat() { return vat; }
    public void setVat(Double vat) { this.vat = vat; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getPaidDate() { return paidDate; }
    public void setPaidDate(Date paidDate) { this.paidDate = paidDate; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}