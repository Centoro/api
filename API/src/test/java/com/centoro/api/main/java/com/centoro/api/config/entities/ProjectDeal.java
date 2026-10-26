package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "project_deals")
public class ProjectDeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "deal_id", unique = true, nullable = false, length = 50)
    private String dealId;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(name = "client_id")
    private Long clientId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;
    
    @Column(columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double value = 0.0;
    
    @Column(name = "score")
    private Integer score = 50;
    
    @Column(length = 50)
    private String stage = "Lead"; // Lead, Qualified, Proposal, Negotiation, Won, Lost
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDealId() { return dealId; }
    public void setDealId(String dealId) { this.dealId = dealId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}