package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "job_cards")
public class JobCard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "job_card_id", unique = true, nullable = false, length = 50)
    private String jobCardId;
    
    @Column(nullable = false)
    private String title;
    
    @Column(name = "client_id")
    private Long clientId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;
    
    @Column(name = "project_id")
    private Long projectId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    private Project project;
    
    @Column(name = "assignee_id")
    private Long assigneeId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", insertable = false, updatable = false)
    private Staff assignee;
    
    @Column(length = 20)
    private String priority = "medium";
    
    @Column(length = 50)
    private String status = "pending";
    
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double hours = 1.0;
    
    @Column(length = 20)
    private String people = "1";
    
    @Column(length = 255)
    private String service;
    
    @Column(length = 100)
    private String type;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "logged_by", length = 255)
    private String loggedBy = "Admin";
    
    @Column(name = "logged_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loggedAt;
    
    @Column(name = "locked_by", length = 255)
    private String lockedBy;
    
    @Column(name = "updated_by", length = 255)
    private String updatedBy;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Column(length = 50)
    private String source = "walk-in";
    
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getJobCardId() { return jobCardId; }
    public void setJobCardId(String jobCardId) { this.jobCardId = jobCardId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    
    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }
    
    public Staff getAssignee() { return assignee; }
    public void setAssignee(Staff assignee) { this.assignee = assignee; }
    
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    
    public Double getHours() { return hours; }
    public void setHours(Double hours) { this.hours = hours; }
    
    public String getPeople() { return people; }
    public void setPeople(String people) { this.people = people; }
    
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getLoggedBy() { return loggedBy; }
    public void setLoggedBy(String loggedBy) { this.loggedBy = loggedBy; }
    
    public Date getLoggedAt() { return loggedAt; }
    public void setLoggedAt(Date loggedAt) { this.loggedAt = loggedAt; }
    
    public String getLockedBy() { return lockedBy; }
    public void setLockedBy(String lockedBy) { this.lockedBy = lockedBy; }
    
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}