package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tracking_rules")
public class TrackingRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rule_id", unique = true, nullable = false, length = 50)
    private String ruleId;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(length = 20)
    private String prefix;
    
    @Column(length = 50)
    private String format;
    
    @Column(length = 50)
    private String applies;
    
    @Column(name = "start_number")
    private Integer startNumber = 1;
    
    @Column(name = "padding")
    private Integer padding = 4;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRuleId() { return ruleId; }
    public void setRuleId(String ruleId) { this.ruleId = ruleId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    public String getApplies() { return applies; }
    public void setApplies(String applies) { this.applies = applies; }
    public Integer getStartNumber() { return startNumber; }
    public void setStartNumber(Integer startNumber) { this.startNumber = startNumber; }
    public Integer getPadding() { return padding; }
    public void setPadding(Integer padding) { this.padding = padding; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}