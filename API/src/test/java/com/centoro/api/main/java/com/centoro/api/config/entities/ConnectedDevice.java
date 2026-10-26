package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "connected_devices")
public class ConnectedDevice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "device_id", unique = true, nullable = false, length = 50)
    private String deviceId;
    
    @Column(name = "device_name", length = 255)
    private String deviceName;
    
    @Column(length = 255)
    private String user;
    
    @Column(length = 100)
    private String browser;
    
    @Column(length = 100)
    private String os;
    
    @Column(name = "ip", length = 45)
    private String ip;
    
    @Column(length = 20)
    private String status = "online";
    
    @Column(name = "last_seen")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSeen;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    
    public String getBrowser() { return browser; }
    public void setBrowser(String browser) { this.browser = browser; }
    
    public String getOs() { return os; }
    public void setOs(String os) { this.os = os; }
    
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getLastSeen() { return lastSeen; }
    public void setLastSeen(Date lastSeen) { this.lastSeen = lastSeen; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}