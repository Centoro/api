package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "phone_lines")
public class PhoneLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "line_id", unique = true, nullable = false, length = 50)
    private String lineId;
    
    @Column(nullable = false, length = 255)
    private String label;
    
    @Column(nullable = false, length = 50)
    private String number;
    
    @Column(length = 50)
    private String type; // VoIP/SIP, Mobile/GSM, Landline/PSTN, Twilio, Custom
    
    @Column(length = 255)
    private String provider;
    
    @Column(name = "sip_server", length = 255)
    private String sipServer;
    
    @Column(length = 255)
    private String username;
    
    @Column(name = "password_hash", length = 255)
    private String passwordHash;
    
    @Column(length = 20)
    private String extension;
    
    @Column(length = 50)
    private String status = "active";
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLineId() { return lineId; }
    public void setLineId(String lineId) { this.lineId = lineId; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getSipServer() { return sipServer; }
    public void setSipServer(String sipServer) { this.sipServer = sipServer; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getExtension() { return extension; }
    public void setExtension(String extension) { this.extension = extension; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}