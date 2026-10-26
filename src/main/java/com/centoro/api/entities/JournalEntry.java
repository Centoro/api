package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "journal_entries")
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "journal_id", unique = true, nullable = false, length = 50)
    private String journalId;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(nullable = false)
    private String description;
    
    @Column(length = 100)
    private String reference;
    
    @Column(name = "source_id", length = 100)
    private String sourceId;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @OneToMany(mappedBy = "journalEntry", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JournalLine> lines;
    
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
    public List<JournalLine> getLines() { return lines; }
    public void setLines(List<JournalLine> lines) { this.lines = lines; }
}