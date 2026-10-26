package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pending_approvals")
public class PendingApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "approval_id", unique = true, nullable = false, length = 50)
    private String approvalId;
    
    @Column(nullable = false, length = 255)
    private String action;
    
    @Column(name = "requested_by", length = 255)
    private String requestedBy;
    
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(columnDefinition = "JSON")
    private String signatures;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getApprovalId() { return approvalId; }
    public void setApprovalId(String approvalId) { this.approvalId = approvalId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getRequestedBy() { return requestedBy; }
    public void setRequestedBy(String requestedBy) { this.requestedBy = requestedBy; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getSignatures() { return signatures; }
    public void setSignatures(String signatures) { this.signatures = signatures; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}