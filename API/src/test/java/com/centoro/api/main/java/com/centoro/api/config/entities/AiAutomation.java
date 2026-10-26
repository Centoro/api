package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ai_automations")
public class AiAutomation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "automation_id", unique = true, nullable = false, length = 50)
    private String automationId;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(length = 100)
    private String schedule;
    
    @Column(columnDefinition = "TEXT")
    private String trigger;
    
    @Column(length = 255)
    private String action;
    
    @Column(name = "ai_provider_id")
    private Long aiProviderId;
    
    @Column(name = "enabled")
    private Boolean enabled = true;
    
    @Column(name = "last_run")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRun;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAutomationId() { return automationId; }
    public void setAutomationId(String automationId) { this.automationId = automationId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
    public String getTrigger() { return trigger; }
    public void setTrigger(String trigger) { this.trigger = trigger; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public Long getAiProviderId() { return aiProviderId; }
    public void setAiProviderId(Long aiProviderId) { this.aiProviderId = aiProviderId; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public Date getLastRun() { return lastRun; }
    public void setLastRun(Date lastRun) { this.lastRun = lastRun; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}