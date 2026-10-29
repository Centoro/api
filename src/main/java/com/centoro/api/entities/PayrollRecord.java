package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payroll_records")
public class PayrollRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "record_id", unique = true, nullable = false, length = 50)
    private String recordId;

    // FIX: Changed to String to match Staff.id (UUID)
    @Column(name = "staff_id", nullable = false, length = 36)
    private String staffId;

    @Column(nullable = false, length = 20)
    private String period;

    @Column(columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double gross = 0.0;

    @Column(columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double paye = 0.0;

    @Column(columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double uif = 0.0;

    @Column(columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double net = 0.0;

    @Column(length = 50)
    private String status = "pending";

    @Column(name = "paid_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paidAt;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public Double getGross() { return gross; }
    public void setGross(Double gross) { this.gross = gross; }

    public Double getPaye() { return paye; }
    public void setPaye(Double paye) { this.paye = paye; }

    public Double getUif() { return uif; }
    public void setUif(Double uif) { this.uif = uif; }

    public Double getNet() { return net; }
    public void setNet(Double net) { this.net = net; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getPaidAt() { return paidAt; }
    public void setPaidAt(Date paidAt) { this.paidAt = paidAt; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
