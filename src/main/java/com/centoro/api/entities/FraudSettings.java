package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fraud_settings")
public class FraudSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "enabled")
    private Boolean enabled = true;
    
    @Column(name = "sideline_detection")
    private Boolean sidelineDetection = true;
    
    @Column(name = "unauthorized_deals")
    private Boolean unauthorizedDeals = true;
    
    @Column(name = "unusual_patterns")
    private Boolean unusualPatterns = true;
    
    @Column(name = "sensitivity_level", length = 20)
    private String sensitivityLevel = "medium";
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public Boolean getSidelineDetection() { return sidelineDetection; }
    public void setSidelineDetection(Boolean sidelineDetection) { this.sidelineDetection = sidelineDetection; }
    public Boolean getUnauthorizedDeals() { return unauthorizedDeals; }
    public void setUnauthorizedDeals(Boolean unauthorizedDeals) { this.unauthorizedDeals = unauthorizedDeals; }
    public Boolean getUnusualPatterns() { return unusualPatterns; }
    public void setUnusualPatterns(Boolean unusualPatterns) { this.unusualPatterns = unusualPatterns; }
    public String getSensitivityLevel() { return sensitivityLevel; }
    public void setSensitivityLevel(String sensitivityLevel) { this.sensitivityLevel = sensitivityLevel; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}