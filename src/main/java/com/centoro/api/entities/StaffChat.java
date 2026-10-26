package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "staff_chats")
public class StaffChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "chat_id", unique = true, nullable = false, length = 50)
    private String chatId;
    
    @Column(nullable = false, length = 50)
    private String type;
    
    @Column(nullable = false)
    private String subject;
    
    @Column(name = "project_id")
    private Long projectId;
    
    @Column(name = "team_id")
    private Long teamId;
    
    @Column(name = "group_name", length = 255)
    private String groupName;
    
    @Column(columnDefinition = "JSON", nullable = false)
    private String participants;
    
    @Column(name = "calendar_date")
    @Temporal(TemporalType.DATE)
    private Date calendarDate;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @OneToMany(mappedBy = "staffChat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StaffChatMessage> messages;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getChatId() { return chatId; }
    public void setChatId(String chatId) { this.chatId = chatId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public String getParticipants() { return participants; }
    public void setParticipants(String participants) { this.participants = participants; }
    public Date getCalendarDate() { return calendarDate; }
    public void setCalendarDate(Date calendarDate) { this.calendarDate = calendarDate; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public List<StaffChatMessage> getMessages() { return messages; }
    public void setMessages(List<StaffChatMessage> messages) { this.messages = messages; }
}