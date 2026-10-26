package com.centoro.api.dto;

import java.util.Date;
import java.util.List;

public class JournalEntryDTO {
    private Long id;
    private String journalId;
    private Date date;
    private String description;
    private String reference;
    private String sourceId;
    private Date createdAt;
    private List<JournalLineDTO> lines;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getJournalId() { return journalId; }
    public void setJournalId(String journalId) { this.journalId = journalId; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getSourceId() { return sourceId; }
    public void setSourceId(String sourceId) { this.sourceId = sourceId; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public List<JournalLineDTO> getLines() { return lines; }
    public void setLines(List<JournalLineDTO> lines) { this.lines = lines; }
}