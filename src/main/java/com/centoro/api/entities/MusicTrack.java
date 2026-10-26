package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "music_tracks")
public class MusicTrack {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "track_id", unique = true, nullable = false, length = 50)
    private String trackId;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 100)
    private String genre;
    
    private Integer bpm;
    
    @Column(length = 10)
    private String key;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double leasePrice = 450.0;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double royaltyFreePrice = 1200.0;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double exclusivePrice = 2500.0;
    
    @Column(name = "cover_url", length = 500)
    private String coverUrl;
    
    @Column(name = "audio_url", length = 500)
    private String audioUrl;
    
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
    
    public String getTrackId() { return trackId; }
    public void setTrackId(String trackId) { this.trackId = trackId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public Integer getBpm() { return bpm; }
    public void setBpm(Integer bpm) { this.bpm = bpm; }
    
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    
    public Double getLeasePrice() { return leasePrice; }
    public void setLeasePrice(Double leasePrice) { this.leasePrice = leasePrice; }
    
    public Double getRoyaltyFreePrice() { return royaltyFreePrice; }
    public void setRoyaltyFreePrice(Double royaltyFreePrice) { this.royaltyFreePrice = royaltyFreePrice; }
    
    public Double getExclusivePrice() { return exclusivePrice; }
    public void setExclusivePrice(Double exclusivePrice) { this.exclusivePrice = exclusivePrice; }
    
    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
    
    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    
    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}
