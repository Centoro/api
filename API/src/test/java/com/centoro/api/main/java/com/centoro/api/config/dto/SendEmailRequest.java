package com.centoro.api.dto;

import java.util.List;
import java.util.Map;

public class SendEmailRequest {
    private Long accountId;
    private Long clientId;
    private String recipientName;
    private String recipientEmail;
    private String subject;
    private String body;
    private List<Map<String, String>> attachments;
    
    // Getters and Setters
    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public List<Map<String, String>> getAttachments() { return attachments; }
    public void setAttachments(List<Map<String, String>> attachments) { this.attachments = attachments; }
}