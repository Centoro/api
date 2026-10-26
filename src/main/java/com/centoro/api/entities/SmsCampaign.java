package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sms_campaigns")
public class SmsCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "campaign_id", unique = true, nullable = false, length = 50)
    private String campaignId;
    
    @Column(columnDefinition = "TEXT")
    private String message;
    
    @Column(length = 255)
    private String recipient;
    
    @Column(name = "recipients_count")
    private Integer recipientsCount = 0;
    
    @Column(length = 255)
    private String provider;
    
    @Column(name = "from_line", length = 255)
    private String fromLine;
    
    @Column(name = "sent_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;
    
    @Column(length = 50)
    private String status = "sent";
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCampaignId() { return campaignId; }
    public void setCampaignId(String campaignId) { this.campaignId = campaignId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public Integer getRecipientsCount() { return recipientsCount; }
    public void setRecipientsCount(Integer recipientsCount) { this.recipientsCount = recipientsCount; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getFromLine() { return fromLine; }
    public void setFromLine(String fromLine) { this.fromLine = fromLine; }
    public Date getSentAt() { return sentAt; }
    public void setSentAt(Date sentAt) { this.sentAt = sentAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}