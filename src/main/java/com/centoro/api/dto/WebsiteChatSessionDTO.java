package com.centoro.api.dto;

import java.util.Date;
import java.util.List;

public class WebsiteChatSessionDTO {
    private Long id;
    private String sessionId;
    private String name;
    private String contact;
    private String department;
    private String service;
    private String source;
    private String status;
    private Integer unread;
    private Date createdAt;
    private Date lastMessageAt;
    private List<WebsiteChatDTO> messages;
    
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
    public List<WebsiteChatDTO> getMessages() { return messages; }
    public void setMessages(List<WebsiteChatDTO> messages) { this.messages = messages; }
}