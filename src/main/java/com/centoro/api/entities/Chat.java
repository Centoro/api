package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "chat_id", unique = true, nullable = false, length = 50)
    private String chatId;
    
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;
    
    @Column(nullable = false)
    private String subject;
    
    @Column(length = 50)
    private String status = "open";
    
    @Column(length = 20)
    private String priority = "medium";
    
    @Column(name = "calendar_date")
    @Temporal(TemporalType.DATE)
    private Date calendarDate;
    
    @Column(name = "linked_job_id")
    private Long linkedJobId;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatMessage> messages;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getChatId() { return chatId; }
    public void setChatId(String chatId) { this.chatId = chatId; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
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
    public List<ChatMessage> getMessages() { return messages; }
    public void setMessages(List<ChatMessage> messages) { this.messages = messages; }
}