package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "payment_id", unique = true, nullable = false, length = 50)
    private String paymentId;
    
    @Column(name = "invoice_id")
    private Long invoiceId;
    
    @Column(nullable = false, columnDefinition = "DECIMAL(18,2)")
    private Double amount;
    
    @Column(length = 50)
    private String method = "EFT";
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(length = 255)
    private String reference;
    
    @Column(length = 50)
    private String source = "invoice";
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}