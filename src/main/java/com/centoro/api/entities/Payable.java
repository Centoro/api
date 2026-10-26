package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payables")
public class Payable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "payable_id", unique = true, nullable = false, length = 50)
    private String payableId;
    
    @Column(nullable = false)
    private String supplier;
    
    @Column(length = 255)
    private String description;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double amount;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double vat = 0.0;
    
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    
    @Column(length = 50)
    private String status = "unpaid";
    
    @Column(name = "paid_date")
    @Temporal(TemporalType.DATE)
    private Date paidDate;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
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
