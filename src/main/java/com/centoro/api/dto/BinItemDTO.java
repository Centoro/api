package com.centoro.api.dto;

import java.util.Date;

public class BinItemDTO {
    private Long id;
    private String binId;
    private String collection;
    private Long originalId;
    private String snapshot;
    private String deletedBy;
    private Date deletedAt;
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