package com.centoro.api.dto;

import java.util.Date;

public class WebsiteStatsDTO {
    private Long id;
    private String statsId;
    private Integer visitors;
    private Integer downloads;
    private Double conversionRate;
    private String topCountry;
    private Date date;
    private Date createdAt;
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