package com.centoro.api.dto;

import java.util.Date;

public class TaxPeriodDTO {
    private Long id;
    private String period;
    private Double outputVat;
    private Double inputVat;
    private Double payable;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public Double getOutputVat() { return outputVat; }
    public void setOutputVat(Double outputVat) { this.outputVat = outputVat; }
    public Double getInputVat() { return inputVat; }
    public void setInputVat(Double inputVat) { this.inputVat = inputVat; }
    public Double getPayable() { return payable; }
    public void setPayable(Double payable) { this.payable = payable; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}