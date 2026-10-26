package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "staff_notifications")
public class StaffNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "notification_id", unique = true, nullable = false, length = 50)
    private String notificationId;
    
    @Column(length = 50)
    private String target; // all, all-depts, specific-dept
    
    @Column(length = 255)
    private String department;
    
    @Column(length = 20)
    private String priority; // low, medium, high, urgent
    
    @Column(columnDefinition = "TEXT")
    private String message;
    
    @Column(name = "sender", length = 255)
    private String sender;
    
    @Column(name = "sender_id", length = 50)
    private String senderId;
    
    @Column(columnDefinition = "JSON")
    private String attachment;
    
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNotificationId() { return notificationId; }
    public void setNotificationId(String notificationId) { this.notificationId = notificationId; }
    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public String getAttachment() { return attachment; }
    public void setAttachment(String attachment) { this.attachment = attachment; }
    public Date getTime() { return time; }
    public void setTime(Date time) { this.time = time; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}