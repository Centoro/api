package com.centoro.api.dto;

import java.util.Date;

public class EmailCampaignDTO {
    private Long id;
    private String campaignId;
    private String subject;
    private String body;
    private String recipient;
    private Integer recipientsCount;
    private String provider;
    private String attachments;
    private String status;
    private Date sentAt;
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCampaignId() { return campaignId; }
    public void setCampaignId(String campaignId) { this.campaignId = campaignId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public Integer getRecipientsCount() { return recipientsCount; }
    public void setRecipientsCount(Integer recipientsCount) { this.recipientsCount = recipientsCount; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getAttachments() { return attachments; }
    public void setAttachments(String attachments) { this.attachments = attachments; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getSentAt() { return sentAt; }
    public void setSentAt(Date sentAt) { this.sentAt = sentAt; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}