package com.centoro.api.dto;

import java.util.Date;

public class AnalyticsDTO {
    private Long id;
    private String analyticsId;
    private String type;
    private String label;
    private String value;
    private Integer count;
    private Integer interactions;
    private String country;
    private String item;
    private String device;
    private Double percentage;
    private Date time;
    private Date createdAt;
    
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
}