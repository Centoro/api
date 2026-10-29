package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "project_messages")
public class ProjectMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "message_id", unique = true, nullable = false, length = 50)
    private String messageId;
    
    // FIX: Changed from Long to String to match Project.id (UUID)
    @Column(name = "project_id", nullable = false, length = 36)
    private String projectId;
    
    // FIX: Changed from Long to String to match Staff.id (UUID)
    @Column(name = "staff_id", length = 36)
    private String staffId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", insertable = false, updatable = false)
    private Staff staff;
    
    @Column(columnDefinition = "TEXT")
    private String text;
    
    @Column(columnDefinition = "TEXT")
    private String image;
    
    @Column(columnDefinition = "JSON")
    private String cc;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getCc() { return cc; }
    public void setCc(String cc) { this.cc = cc; }
    public Date getTime() { return time; }
    public void setTime(Date time) { this.time = time; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
