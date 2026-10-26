package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment_providers")
public class PaymentProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "provider_id", unique = true, nullable = false, length = 50)
    private String providerId;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(name = "merchant_id", length = 255)
    private String merchantId;
    
    @Column(name = "public_key", length = 500)
    private String publicKey;
    
    @Column(name = "secret_key", length = 500)
    private String secretKey;
    
    @Column(length = 500)
    private String webhook;
    
    @Column(length = 50)
    private String status = "connected";
    
    @Column(name = "connected_at")
    @Temporal(TemporalType.TIMESTAMP)
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
    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    public String getPublicKey() { return publicKey; }
    public void setPublicKey(String publicKey) { this.publicKey = publicKey; }
    public String getSecretKey() { return secretKey; }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
    public String getWebhook() { return webhook; }
    public void setWebhook(String webhook) { this.webhook = webhook; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getConnectedAt() { return connectedAt; }
    public void setConnectedAt(Date connectedAt) { this.connectedAt = connectedAt; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}