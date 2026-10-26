package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tax_periods")
public class TaxPeriod {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 20)
    private String period;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double outputVat = 0.0;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double inputVat = 0.0;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double payable = 0.0;
    
    @Column(length = 50)
    private String status = "pending";
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
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
