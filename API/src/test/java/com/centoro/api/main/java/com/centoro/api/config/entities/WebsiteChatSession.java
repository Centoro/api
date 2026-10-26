package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "website_chat_sessions")
public class WebsiteChatSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "session_id", unique = true, nullable = false, length = 100)
    private String sessionId;
    
    @Column(length = 255)
    private String name;
    
    @Column(length = 255)
    private String contact;
    
    @Column(length = 100)
    private String department;
    
    @Column(length = 255)
    private String service;
    
    @Column(length = 100)
    private String source;
    
    @Column(length = 50)
    private String status = "open";
    
    @Column(name = "unread")
    private Integer unread = 0;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "last_message_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastMessageAt;
    
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WebsiteChat> messages;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Integer getUnread() { return unread; }
    public void setUnread(Integer unread) { this.unread = unread; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public Date getLastMessageAt() { return lastMessageAt; }
    public void setLastMessageAt(Date lastMessageAt) { this.lastMessageAt = lastMessageAt; }
    
    public List<WebsiteChat> getMessages() { return messages; }
    public void setMessages(List<WebsiteChat> messages) { this.messages = messages; }
}