package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fraud_alerts")
public class FraudAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fraud_id", unique = true, nullable = false, length = 50)
    private String fraudId;
    
    @Column(nullable = false, length = 100)
    private String type;
    
    @Column(name = "staff_name", length = 255)
    private String staffName;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String pattern;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double amount = 0.0;
    
    @Column(length = 20)
    private String severity = "medium";
    
    @Column(length = 50)
    private String status = "active";
    
    @Column(name = "detected_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date detectedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFraudId() { return fraudId; }
    public void setFraudId(String fraudId) { this.fraudId = fraudId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPattern() { return pattern; }
    public void setPattern(String pattern) { this.pattern = pattern; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getDetectedAt() { return detectedAt; }
    public void setDetectedAt(Date detectedAt) { this.detectedAt = detectedAt; }
}