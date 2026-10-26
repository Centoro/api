package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "security_settings")
public class SecuritySettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "stealth")
    private Boolean stealth = true;
    
    @Column(name = "ddos")
    private Boolean ddos = true;
    
    @Column(name = "ids")
    private Boolean ids = true;
    
    @Column(name = "encrypt")
    private Boolean encrypt = true;
    
    @Column(name = "honeypot")
    private Boolean honeypot = true;
    
    @Column(name = "autoban")
    private Boolean autoban = true;
    
    @Column(name = "min_pass")
    private Integer minPass = 12;
    
    @Column(name = "session_timeout")
    private Integer sessionTimeout = 30;
    
    @Column(name = "two_fa")
    private String twoFA = "required";
    
    @Column(name = "max_attempts")
    private Integer maxAttempts = 3;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getStealth() { return stealth; }
    public void setStealth(Boolean stealth) { this.stealth = stealth; }
    public Boolean getDdos() { return ddos; }
    public void setDdos(Boolean ddos) { this.ddos = ddos; }
    public Boolean getIds() { return ids; }
    public void setIds(Boolean ids) { this.ids = ids; }
    public Boolean getEncrypt() { return encrypt; }
    public void setEncrypt(Boolean encrypt) { this.encrypt = encrypt; }
    public Boolean getHoneypot() { return honeypot; }
    public void setHoneypot(Boolean honeypot) { this.honeypot = honeypot; }
    public Boolean getAutoban() { return autoban; }
    public void setAutoban(Boolean autoban) { this.autoban = autoban; }
    public Integer getMinPass() { return minPass; }
    public void setMinPass(Integer minPass) { this.minPass = minPass; }
    public Integer getSessionTimeout() { return sessionTimeout; }
    public void setSessionTimeout(Integer sessionTimeout) { this.sessionTimeout = sessionTimeout; }
    public String getTwoFA() { return twoFA; }
    public void setTwoFA(String twoFA) { this.twoFA = twoFA; }
    public Integer getMaxAttempts() { return maxAttempts; }
    public void setMaxAttempts(Integer maxAttempts) { this.maxAttempts = maxAttempts; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}