package com.centoro.api.dto;

import java.util.Date;
import java.util.List;

public class ChatDTO {
    private Long id;
    private String chatId;
    private Long clientId;
    private String clientName;
    private String subject;
    private String status;
    private String priority;
    private Date calendarDate;
    private Long linkedJobId;
    private Date createdAt;
    private Date updatedAt;
    private List<ChatMessageDTO> messages;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getChatId() { return chatId; }
    public void setChatId(String chatId) { this.chatId = chatId; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public Date getCalendarDate() { return calendarDate; }
    public void setCalendarDate(Date calendarDate) { this.calendarDate = calendarDate; }
    public Long getLinkedJobId() { return linkedJobId; }
    public void setLinkedJobId(Long linkedJobId) { this.linkedJobId = linkedJobId; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public List<ChatMessageDTO> getMessages() { return messages; }
    public void setMessages(List<ChatMessageDTO> messages) { this.messages = messages; }
}