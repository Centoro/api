package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "calendar_notes")
public class CalendarNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "note_id", unique = true, nullable = false, length = 50)
    private String noteId;
    
    @Column(nullable = false)
    private String date;
    
    @Column(name = "project_id")
    private Long projectId;
    
    @Column(columnDefinition = "TEXT")
    private String text;
    
    @Column(length = 20)
    private String importance = "medium";
    
    @Column(name = "created_by", length = 255)
    private String createdBy;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNoteId() { return noteId; }
    public void setNoteId(String noteId) { this.noteId = noteId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getImportance() { return importance; }
    public void setImportance(String importance) { this.importance = importance; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}