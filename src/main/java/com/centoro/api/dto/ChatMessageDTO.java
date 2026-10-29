package com.centoro.api.dto;

import java.util.Date;

public class ChatMessageDTO {
    private Long id;
    private String messageId;
    private Long chatId;
    private String from;
    private String staffId;  // FIX
    private String staffName;
    private String text;
    private String attachment;
    private Date createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public Long getChatId() { return chatId; }
    public void setChatId(Long chatId) { this.chatId = chatId; }
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getAttachment() { return attachment; }
    public void setAttachment(String attachment) { this.attachment = attachment; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
