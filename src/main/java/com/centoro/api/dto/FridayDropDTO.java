package com.centoro.api.dto;

import java.util.Date;

public class FridayDropDTO {
    private Long id;
    private String dropId;
    private String title;
    private String type;
    private String genre;
    private Date date;
    private Date expiry;
    private Integer bpm;
    private Double discount;
    private Double price;
    private Integer slots;
    private Integer claimed;
    private String coverUrl;
    private String audioUrl;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDropId() { return dropId; }
    public void setDropId(String dropId) { this.dropId = dropId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public Date getExpiry() { return expiry; }
    public void setExpiry(Date expiry) { this.expiry = expiry; }
    public Integer getBpm() { return bpm; }
    public void setBpm(Integer bpm) { this.bpm = bpm; }
    public Double getDiscount() { return discount; }
    public void setDiscount(Double discount) { this.discount = discount; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getSlots() { return slots; }
    public void setSlots(Integer slots) { this.slots = slots; }
    public Integer getClaimed() { return claimed; }
    public void setClaimed(Integer claimed) { this.claimed = claimed; }
    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}