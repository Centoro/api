package com.centoro.api.dto;

import java.util.Date;

public class StaffChatMessageDTO {
    private Long id;
    private String messageId;
    private Long chatId;
    private Long staffId;
    private String staffName;
    private String text;
    private String attachment;
    private Boolean system;
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public Long getChatId() { return chatId; }
    public void setChatId(Long chatId) { this.chatId = chatId; }
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getAttachment() { return attachment; }
    public void setAttachment(String attachment) { this.attachment = attachment; }
    public Boolean getSystem() { return system; }
    public void setSystem(Boolean system) { this.system = system; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}