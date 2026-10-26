package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "project_automations")
public class ProjectAutomation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "automation_id", unique = true, nullable = false, length = 50)
    private String automationId;
    
    @Column(name = "trigger_event", nullable = false, length = 100)  // FIXED: Renamed from "trigger" to avoid MySQL reserved keyword
    private String trigger;
    
    @Column(nullable = false, length = 100)
    private String action;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAutomationId() { return automationId; }
    public void setAutomationId(String automationId) { this.automationId = automationId; }
    public String getTrigger() { return trigger; }
    public void setTrigger(String trigger) { this.trigger = trigger; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}