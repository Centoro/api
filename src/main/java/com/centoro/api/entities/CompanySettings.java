package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "company_settings")
public class CompanySettings {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "company_name")
    private String companyName = "Centoro Enterprise";
    
    @Column(name = "trading_as")
    private String tradingAs;
    
    @Column(name = "vat_rate")
    private Double vatRate = 15.0;
    
    private String currency = "ZAR";
    
    @Column(name = "override_code")
    private String overrideCode;
    
    private String country = "South Africa";
    
    @Column(name = "reg_number")
    private String regNumber;
    
    @Column(name = "tax_number")
    private String taxNumber;
    
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    
    @Column(name = "theme_primary")
    private String themePrimary = "#f59e0b";
    
    @Column(name = "theme_primary_dark")
    private String themePrimaryDark = "#d97706";
    
    @Column(name = "theme_primary_light")
    private String themePrimaryLight = "#fbbf24";
    
    @Column(name = "theme_accent")
    private String themeAccent = "#3b82f6";
    
    @Column(name = "theme_bg_base")
    private String themeBgBase = "#0f172a";
    
    @Column(name = "theme_bg_surface")
    private String themeBgSurface = "#1e293b";
    
    @Column(name = "revenue_target_2026")
    private Double revenueTarget2026 = 300000.0;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    
    public String getTradingAs() { return tradingAs; }
    public void setTradingAs(String tradingAs) { this.tradingAs = tradingAs; }
    
    public Double getVatRate() { return vatRate; }
    public void setVatRate(Double vatRate) { this.vatRate = vatRate; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getOverrideCode() { return overrideCode; }
    public void setOverrideCode(String overrideCode) { this.overrideCode = overrideCode; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getRegNumber() { return regNumber; }
    public void setRegNumber(String regNumber) { this.regNumber = regNumber; }
    
    public String getTaxNumber() { return taxNumber; }
    public void setTaxNumber(String taxNumber) { this.taxNumber = taxNumber; }
    
    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
    
    public String getThemePrimary() { return themePrimary; }
    public void setThemePrimary(String themePrimary) { this.themePrimary = themePrimary; }
    
    public String getThemePrimaryDark() { return themePrimaryDark; }
    public void setThemePrimaryDark(String themePrimaryDark) { this.themePrimaryDark = themePrimaryDark; }
    
    public String getThemePrimaryLight() { return themePrimaryLight; }
    public void setThemePrimaryLight(String themePrimaryLight) { this.themePrimaryLight = themePrimaryLight; }
    
    public String getThemeAccent() { return themeAccent; }
    public void setThemeAccent(String themeAccent) { this.themeAccent = themeAccent; }
    
    public String getThemeBgBase() { return themeBgBase; }
    public void setThemeBgBase(String themeBgBase) { this.themeBgBase = themeBgBase; }
    
    public String getThemeBgSurface() { return themeBgSurface; }
    public void setThemeBgSurface(String themeBgSurface) { this.themeBgSurface = themeBgSurface; }
    
    public Double getRevenueTarget2026() { return revenueTarget2026; }
    public void setRevenueTarget2026(Double revenueTarget2026) { this.revenueTarget2026 = revenueTarget2026; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}