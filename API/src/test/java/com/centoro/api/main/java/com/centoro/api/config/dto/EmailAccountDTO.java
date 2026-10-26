package com.centoro.api.dto;

import java.util.Date;

public class EmailAccountDTO {
    private Long id;
    private String accountId;
    private String type;
    private String label;
    private String email;
    private String status;
    private Date connectedAt;
    private Date createdAt;
    
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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getConnectedAt() { return connectedAt; }
    public void setConnectedAt(Date connectedAt) { this.connectedAt = connectedAt; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}