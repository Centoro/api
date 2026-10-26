package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "website_stats")
public class WebsiteStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "stats_id", unique = true, nullable = false, length = 50)
    private String statsId;
    
    @Column(nullable = false)
    private Integer visitors = 0;
    
    @Column(nullable = false)
    private Integer downloads = 0;
    
    @Column(name = "conversion_rate")
    private Double conversionRate = 0.0;
    
    @Column(name = "top_country", length = 100)
    private String topCountry;
    
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStatsId() { return statsId; }
    public void setStatsId(String statsId) { this.statsId = statsId; }
    public Integer getVisitors() { return visitors; }
    public void setVisitors(Integer visitors) { this.visitors = visitors; }
    public Integer getDownloads() { return downloads; }
    public void setDownloads(Integer downloads) { this.downloads = downloads; }
    public Double getConversionRate() { return conversionRate; }
    public void setConversionRate(Double conversionRate) { this.conversionRate = conversionRate; }
    public String getTopCountry() { return topCountry; }
    public void setTopCountry(String topCountry) { this.topCountry = topCountry; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}