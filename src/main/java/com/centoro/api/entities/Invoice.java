package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "invoices")
public class Invoice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;
    
    @Column(name = "invoice_id", unique = true, nullable = false, length = 50)
    private String invoiceId;
    
    @Column(name = "tracking_number", unique = true, nullable = false, length = 100)
    private String trackingNumber;
    
    // FIX: Changed from Long to String
    @Column(name = "client_id", nullable = false, length = 36)
    private String clientId;
    
    // FIX: Changed from Long to String
    @Column(name = "project_id", length = 36)
    private String projectId;
    
    @Column(length = 255)
    private String service;
    
    @Column(columnDefinition = "JSON")
    private String items;
    
    @Column(columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double subtotal = 0.0;
    
    @Column(name = "discount_pct", columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private Double discountPct = 0.0;
    
    @Column(columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double vat = 0.0;
    
    @Column(columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double total = 0.0;
    
    @Column(columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double paid = 0.0;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private InvoiceStatus status = InvoiceStatus.unpaid;
    
    @Column(name = "issue_date")
    @Temporal(TemporalType.DATE)
    private Date issueDate;
    
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    
    @Column(name = "last_reminder_date")
    @Temporal(TemporalType.DATE)
    private Date lastReminderDate;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    
    public enum InvoiceStatus {
        unpaid, partial, paid, overdue
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    public Double getDiscountPct() { return discountPct; }
    public void setDiscountPct(Double discountPct) { this.discountPct = discountPct; }
    public Double getVat() { return vat; }
    public void setVat(Double vat) { this.vat = vat; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public Double getPaid() { return paid; }
    public void setPaid(Double paid) { this.paid = paid; }
    public InvoiceStatus getStatus() { return status; }
    public void setStatus(InvoiceStatus status) { this.status = status; }
    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public Date getLastReminderDate() { return lastReminderDate; }
    public void setLastReminderDate(Date lastReminderDate) { this.lastReminderDate = lastReminderDate; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}
