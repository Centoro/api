package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "staff_warnings")
public class StaffWarning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "warning_id", unique = true, nullable = false, length = 50)
    private String warningId;
    
    @Column(name = "staff_id", nullable = false)
    private Long staffId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", insertable = false, updatable = false)
    private Staff staff;
    
    @Column(nullable = false, length = 100)
    private String type; // Verbal Warning, Written Warning, Final Written Warning, Suspension Notice
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;
    
    @Column(name = "issued_by", length = 255)
    private String issuedBy;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getWarningId() { return warningId; }
    public void setWarningId(String warningId) { this.warningId = warningId; }
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }
    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getIssuedBy() { return issuedBy; }
    public void setIssuedBy(String issuedBy) { this.issuedBy = issuedBy; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}