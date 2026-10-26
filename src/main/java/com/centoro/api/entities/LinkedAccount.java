package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "linked_accounts")
public class LinkedAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "account_id", unique = true, nullable = false, length = 50)
    private String accountId;
    
    @Column(nullable = false, length = 100)
    private String platform;
    
    @Column(nullable = false, length = 255)
    private String username;
    
    @Column(name = "password_hash", length = 255)
    private String passwordHash;
    
    @Column(name = "page_id", length = 255)
    private String pageId;
    
    @Column(name = "two_fa", length = 20)
    private String twoFA;
    
    @Column(name = "linked_at")
    @Temporal(TemporalType.DATE)
    private Date linkedAt;
    
    @Column(name = "visible")
    private Boolean visible = true;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getPageId() { return pageId; }
    public void setPageId(String pageId) { this.pageId = pageId; }
    public String getTwoFA() { return twoFA; }
    public void setTwoFA(String twoFA) { this.twoFA = twoFA; }
    public Date getLinkedAt() { return linkedAt; }
    public void setLinkedAt(Date linkedAt) { this.linkedAt = linkedAt; }
    public Boolean getVisible() { return visible; }
    public void setVisible(Boolean visible) { this.visible = visible; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}