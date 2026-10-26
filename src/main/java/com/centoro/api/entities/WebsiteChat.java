package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "website_chats")
public class WebsiteChat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "chat_msg_id", unique = true, nullable = false, length = 50)
    private String chatMsgId;
    
    @Column(name = "session_id", nullable = false, length = 100)
    private String sessionId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", referencedColumnName = "session_id", insertable = false, updatable = false)
    private WebsiteChatSession session;
    
    @Column(nullable = false, length = 20)
    private String from;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;
    
    @Column(length = 100)
    private String department;
    
    @Column(length = 255)
    private String service;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getChatMsgId() { return chatMsgId; }
    public void setChatMsgId(String chatMsgId) { this.chatMsgId = chatMsgId; }
    
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    
    public WebsiteChatSession getSession() { return session; }
    public void setSession(WebsiteChatSession session) { this.session = session; }
    
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}