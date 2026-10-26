package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "analytics")
public class Analytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "analytics_id", unique = true, nullable = false, length = 50)
    private String analyticsId;
    
    @Column(nullable = false)
    private String type; // visitor, download, interaction, device, country
    
    @Column(length = 255)
    private String label;
    
    @Column(length = 255)
    private String value;
    
    @Column(name = "count")
    private Integer count = 0;
    
    @Column(name = "interactions")
    private Integer interactions = 0;
    
    @Column(name = "country")
    private String country;
    
    @Column(name = "item")
    private String item;
    
    @Column(name = "device")
    private String device;
    
    @Column(name = "percentage")
    private Double percentage = 0.0;
    
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAnalyticsId() { return analyticsId; }
    public void setAnalyticsId(String analyticsId) { this.analyticsId = analyticsId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
    public Integer getInteractions() { return interactions; }
    public void setInteractions(Integer interactions) { this.interactions = interactions; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }
    public String getDevice() { return device; }
    public void setDevice(String device) { this.device = device; }
    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
    public Date getTime() { return time; }
    public void setTime(Date time) { this.time = time; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}