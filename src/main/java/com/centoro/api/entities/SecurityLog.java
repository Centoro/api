package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "security_log")
public class SecurityLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    
    @Column(length = 255)
    private String user;
    
    @Column(nullable = false)
    private String event;
    
    @Column(length = 50)
    private String status = "success";
    
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    
    @Column(name = "user_agent", length = 500)
    private String userAgent;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Date getTime() { return time; }
    public void setTime(Date time) { this.time = time; }
    
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    
    public String getEvent() { return event; }
    public void setEvent(String event) { this.event = event; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
}