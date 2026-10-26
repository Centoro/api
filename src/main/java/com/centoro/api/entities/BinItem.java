package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bin_items")
public class BinItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "bin_id", unique = true, nullable = false, length = 50)
    private String binId;
    
    @Column(nullable = false)
    private String collection; // clients, projects, invoices, jobcards, etc.
    
    @Column(name = "original_id")
    private Long originalId;
    
    @Column(columnDefinition = "JSON")
    private String snapshot;
    
    @Column(name = "deleted_by", length = 255)
    private String deletedBy;
    
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBinId() { return binId; }
    public void setBinId(String binId) { this.binId = binId; }
    public String getCollection() { return collection; }
    public void setCollection(String collection) { this.collection = collection; }
    public Long getOriginalId() { return originalId; }
    public void setOriginalId(Long originalId) { this.originalId = originalId; }
    public String getSnapshot() { return snapshot; }
    public void setSnapshot(String snapshot) { this.snapshot = snapshot; }
    public String getDeletedBy() { return deletedBy; }
    public void setDeletedBy(String deletedBy) { this.deletedBy = deletedBy; }
    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}