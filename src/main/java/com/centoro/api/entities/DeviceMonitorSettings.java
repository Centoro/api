package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "device_monitor_settings")
public class DeviceMonitorSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "enabled")
    private Boolean enabled = true;
    
    @Column(name = "alert_on_new_device")
    private Boolean alertOnNewDevice = true;
    
    @Column(name = "max_sessions")
    private Integer maxSessions = 10;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public Boolean getAlertOnNewDevice() { return alertOnNewDevice; }
    public void setAlertOnNewDevice(Boolean alertOnNewDevice) { this.alertOnNewDevice = alertOnNewDevice; }
    public Integer getMaxSessions() { return maxSessions; }
    public void setMaxSessions(Integer maxSessions) { this.maxSessions = maxSessions; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}