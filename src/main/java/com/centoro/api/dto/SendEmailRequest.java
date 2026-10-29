package com.centoro.api.dto;

public class SendEmailRequest {
    private Long accountId;
    private String clientId; // FIX: Changed from Long to String
    private String recipientName;
    private String recipientEmail;
    private String subject;
    private String body;
    private java.util.List<java.util.Map<String, Object>> attachments;
    
    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public java.util.List<java.util.Map<String, Object>> getAttachments() { return attachments; }
    public void setAttachments(java.util.List<java.util.Map<String, Object>> attachments) { this.attachments = attachments; }
}
