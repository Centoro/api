package com.centoro.api.dto;

import java.util.Date;

public class DiscountDTO {
    private Long id;
    private String discountId;
    private Date date;
    private String ref;
    private Long clientId;
    private String clientName;
    private String description;
    private Double discountPct;
    private Double discountAmount;
    private String source;
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDiscountId() { return discountId; }
    public void setDiscountId(String discountId) { this.discountId = discountId; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getDiscountPct() { return discountPct; }
    public void setDiscountPct(Double discountPct) { this.discountPct = discountPct; }
    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}