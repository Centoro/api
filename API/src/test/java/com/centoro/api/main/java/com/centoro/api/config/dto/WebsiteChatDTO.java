package com.centoro.api.dto;

import java.util.Date;

public class WebsiteChatDTO {
    private Long id;
    private String chatMsgId;
    private String sessionId;
    private String from;
    private String text;
    private String department;
    private String service;
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getChatMsgId() { return chatMsgId; }
    public void setChatMsgId(String chatMsgId) { this.chatMsgId = chatMsgId; }
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
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