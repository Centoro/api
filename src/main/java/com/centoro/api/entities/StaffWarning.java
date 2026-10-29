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

    // ✅ FIX: Changed from Long to String
    @Column(name = "staff_id", length = 36)
    private String staffId;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getWarningId() { return warningId; }
    public void setWarningId(String warningId) { this.warningId = warningId; }
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
