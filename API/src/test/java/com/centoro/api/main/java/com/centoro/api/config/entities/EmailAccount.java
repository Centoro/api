package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "email_accounts")
public class EmailAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "account_id", unique = true, nullable = false, length = 50)
    private String accountId;
    
    @Column(nullable = false, length = 50)
    private String type; // google, microsoft, imap, thirdparty, ai
    
    @Column(length = 255)
    private String label;
    
    @Column(length = 255)
    private String email;
    
    @Column(name = "app_password", length = 255)
    private String appPassword;
    
    @Column(name = "imap_host", length = 255)
    private String imapHost;
    
    @Column(name = "imap_port", length = 10)
    private String imapPort;
    
    @Column(length = 255)
    private String username;
    
    @Column(name = "password_hash", length = 255)
    private String passwordHash;
    
    @Column(name = "smtp_host", length = 255)
    private String smtpHost;
    
    @Column(name = "smtp_port", length = 10)
    private String smtpPort;
    
    @Column(name = "api_key", length = 500)
    private String apiKey;
    
    @Column(name = "from_email", length = 255)
    private String fromEmail;
    
    @Column(name = "ai_provider_id", length = 50)
    private String aiProviderId;
    
    @Column(length = 50)
    private String status = "connected";
    
    @Column(name = "connected_at")
    @Temporal(TemporalType.DATE)
    private Date connectedAt;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAppPassword() { return appPassword; }
    public void setAppPassword(String appPassword) { this.appPassword = appPassword; }
    public String getImapHost() { return imapHost; }
    public void setImapHost(String imapHost) { this.imapHost = imapHost; }
    public String getImapPort() { return imapPort; }
    public void setImapPort(String imapPort) { this.imapPort = imapPort; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getSmtpHost() { return smtpHost; }
    public void setSmtpHost(String smtpHost) { this.smtpHost = smtpHost; }
    public String getSmtpPort() { return smtpPort; }
    public void setSmtpPort(String smtpPort) { this.smtpPort = smtpPort; }
    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    public String getFromEmail() { return fromEmail; }
    public void setFromEmail(String fromEmail) { this.fromEmail = fromEmail; }
    public String getAiProviderId() { return aiProviderId; }
    public void setAiProviderId(String aiProviderId) { this.aiProviderId = aiProviderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getConnectedAt() { return connectedAt; }
    public void setConnectedAt(Date connectedAt) { this.connectedAt = connectedAt; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}