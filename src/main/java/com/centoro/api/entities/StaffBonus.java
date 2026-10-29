package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "staff_bonuses")
public class StaffBonus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bonus_id", unique = true, nullable = false, length = 50)
    private String bonusId;

    // ✅ FIX: Changed from Long to String to match Staff.id
    @Column(name = "staff_id", length = 36)
    private String staffId;

    @Column(columnDefinition = "DECIMAL(15,2)")
    private Double amount;

    @Column(length = 255)
    private String reason;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBonusId() { return bonusId; }
    public void setBonusId(String bonusId) { this.bonusId = bonusId; }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
