package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "infrastructure")
public class Infrastructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "infra_id", unique = true, nullable = false, length = 50)
    private String infraId;
    
    @Column(nullable = false, length = 100)
    private String type;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(length = 500)
    private String host;
    
    @Column(length = 20)
    private String port;
    
    @Column(length = 255)
    private String username;
    
    @Column(name = "password_hash", length = 255)
    private String passwordHash;
    
    @Column(length = 255)
    private String bucket;
    
    @Column(length = 10)
    private String readPermission = "yes";
    
    @Column(length = 10)
    private String writePermission = "yes";
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getInfraId() { return infraId; }
    public void setInfraId(String infraId) { this.infraId = infraId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }
    public String getPort() { return port; }
    public void setPort(String port) { this.port = port; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getBucket() { return bucket; }
    public void setBucket(String bucket) { this.bucket = bucket; }
    public String getReadPermission() { return readPermission; }
    public void setReadPermission(String readPermission) { this.readPermission = readPermission; }
    public String getWritePermission() { return writePermission; }
    public void setWritePermission(String writePermission) { this.writePermission = writePermission; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}