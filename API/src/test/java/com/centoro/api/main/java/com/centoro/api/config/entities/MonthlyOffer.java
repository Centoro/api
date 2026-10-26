package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "monthly_offers")
public class MonthlyOffer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "offer_id", unique = true, nullable = false, length = 50)
    private String offerId;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 50)
    private String type = "service";
    
    @Column(length = 100)
    private String genre;
    
    @Column(length = 20)
    private String month;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Temporal(TemporalType.DATE)
    private Date expiry;
    
    private Integer bpm;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double discount;

    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double price;
    
    private Integer slots;
    
    @Column(name = "claimed")
    private Integer claimed = 0;
    
    @Column(name = "cover_url", length = 500)
    private String coverUrl;
    
    @Column(name = "audio_url", length = 500)
    private String audioUrl;
    
    @Column(length = 50)
    private String status = "live";
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getOfferId() { return offerId; }
    public void setOfferId(String offerId) { this.offerId = offerId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    
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
    
    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}
