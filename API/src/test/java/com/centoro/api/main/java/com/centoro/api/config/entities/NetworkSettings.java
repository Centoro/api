package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "network_settings")
public class NetworkSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "auto_connect")
    private Boolean autoConnect = true;
    
    @Column(name = "preferred_network", length = 255)
    private String preferredNetwork;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getAutoConnect() { return autoConnect; }
    public void setAutoConnect(Boolean autoConnect) { this.autoConnect = autoConnect; }
    public String getPreferredNetwork() { return preferredNetwork; }
    public void setPreferredNetwork(String preferredNetwork) { this.preferredNetwork = preferredNetwork; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}