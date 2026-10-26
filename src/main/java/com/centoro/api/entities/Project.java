package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "projects")
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "project_id", unique = true, nullable = false, length = 50)
    private String projectId;
    
    @Column(length = 100)
    private String code;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    
    @Column(length = 255)
    private String service;
    
    @Column(columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double budget = 0.0;
    
    @Column(columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double spent = 0.0;
    
    private Integer progress = 0;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ProjectStatus status = ProjectStatus.planning;
    
    @Column(columnDefinition = "JSON")
    private String assignees;
    
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    
    @Column(length = 20)
    private String priority = "medium";
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    
    public enum ProjectStatus {
        planning, in_progress, completed
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }
    public Double getSpent() { return spent; }
    public void setSpent(Double spent) { this.spent = spent; }
    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }
    public ProjectStatus getStatus() { return status; }
    public void setStatus(ProjectStatus status) { this.status = status; }
    public String getAssignees() { return assignees; }
    public void setAssignees(String assignees) { this.assignees = assignees; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}