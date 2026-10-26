package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "staff_bonuses")
public class StaffBonus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "bonus_id", unique = true)
    private String bonusId;
    
    @Column(name = "staff_id", nullable = false)
    private Long staffId;
    
    private String type = "performance";
    private Double amount = 0.0;
    private Integer performance = 0;
    private String reason;
    private String status = "pending";
    private Date date;
    
    @Column(name = "created_by")
    private String createdBy;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getBonusId() { return bonusId; }
    public void setBonusId(String bonusId) { this.bonusId = bonusId; }
    
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    
    public Integer getPerformance() { return performance; }
    public void setPerformance(Integer performance) { this.performance = performance; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}