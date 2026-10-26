package com.centoro.api.dto;

import java.util.Date;

public class PayrollDTO {
    private Long id;
    private String recordId;
    private Long staffId;
    private String staffName;
    private String role;
    private String period;
    private Double gross;
    private Double paye;
    private Double uif;
    private Double net;
    private String status;
    private Date paidAt;
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
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
}