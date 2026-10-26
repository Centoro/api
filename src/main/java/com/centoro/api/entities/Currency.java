package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "currencies")
public class Currency {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 10)
    private String code;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 10)
    private String symbol;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double ratePerZar = 1.0;
    
    @Column(name = "is_base")
    private Boolean isBase = false;
    
    @Column(length = 100)
    private String country;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public Double getRatePerZar() { return ratePerZar; }
    public void setRatePerZar(Double ratePerZar) { this.ratePerZar = ratePerZar; }
    
    public Boolean getIsBase() { return isBase; }
    public void setIsBase(Boolean isBase) { this.isBase = isBase; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
