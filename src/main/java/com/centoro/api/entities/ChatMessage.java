package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "client_chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id", unique = true, nullable = false, length = 50)
    private String messageId;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", insertable = false, updatable = false)
    private Chat chat;

    @Column(nullable = false, length = 20)
    private String from;

    @Column(name = "staff_id", length = 36)
    private String staffId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", insertable = false, updatable = false)
    private Staff staff;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(columnDefinition = "JSON")
    private String attachment;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public Long getChatId() { return chatId; }
    public void setChatId(Long chatId) { this.chatId = chatId; }
    public Chat getChat() { return chat; }
    public void setChat(Chat chat) { this.chat = chat; }
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getAttachment() { return attachment; }
    public void setAttachment(String attachment) { this.attachment = attachment; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
