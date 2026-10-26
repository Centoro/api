package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vouchers")
public class Voucher {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "voucher_id", unique = true, nullable = false, length = 50)
    private String voucherId;
    
    @Column(unique = true, nullable = false, length = 50)
    private String code;
    
    @Column(name = "promo_code", length = 50)
    private String promoCode;
    
    @Column(length = 20)
    private String type = "percent";
    
    @Column(columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double value = 0.0;
    
    @Column(length = 100)
    private String category = "All Services";
    
    @Column(name = "client_id")
    private Long clientId;
    
    @Column(name = "is_public")
    private Boolean isPublic = true;
    
    @Column(name = "max_uses")
    private Integer maxUses = 10;
    
    @Column(name = "used_count")
    private Integer usedCount = 0;
    
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Column(name = "expiry_date")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;
    
    @Column(length = 50)
    private String purpose = "standard";
    
    @Column(name = "recipient_country", length = 100)
    private String recipientCountry;
    
    @Column(name = "recipient_label", length = 255)
    private String recipientLabel;
    
    @Column(name = "other_first_name", length = 100)
    private String otherFirstName;
    
    @Column(name = "other_surname", length = 100)
    private String otherSurname;
    
    @Column(name = "other_initials", length = 10)
    private String otherInitials;
    
    @Column(name = "other_phone", length = 50)
    private String otherPhone;
    
    @Column(name = "other_email", length = 255)
    private String otherEmail;
    
    @Column(name = "other_address", columnDefinition = "TEXT")
    private String otherAddress;
    
    @Column(length = 255)
    private String company;
    
    @Column(name = "company_ck", length = 50)
    private String companyCK;
    
    @Column(name = "custom_bg", columnDefinition = "LONGTEXT")
    private String customBg;
    
    @Column(length = 50)
    private String status = "active";
    
    @Column(name = "issued_date")
    @Temporal(TemporalType.DATE)
    private Date issuedDate;
    
    @Column(name = "issued_by", length = 255)
    private String issuedBy;
    
    @Column(name = "updated_by", length = 255)
    private String updatedBy;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getVoucherId() { return voucherId; }
    public void setVoucherId(String voucherId) { this.voucherId = voucherId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public Boolean getIsPublic() { return isPublic; }
    public void setIsPublic(Boolean isPublic) { this.isPublic = isPublic; }
    public Integer getMaxUses() { return maxUses; }
    public void setMaxUses(Integer maxUses) { this.maxUses = maxUses; }
    public Integer getUsedCount() { return usedCount; }
    public void setUsedCount(Integer usedCount) { this.usedCount = usedCount; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public String getRecipientCountry() { return recipientCountry; }
    public void setRecipientCountry(String recipientCountry) { this.recipientCountry = recipientCountry; }
    public String getRecipientLabel() { return recipientLabel; }
    public void setRecipientLabel(String recipientLabel) { this.recipientLabel = recipientLabel; }
    public String getOtherFirstName() { return otherFirstName; }
    public void setOtherFirstName(String otherFirstName) { this.otherFirstName = otherFirstName; }
    public String getOtherSurname() { return otherSurname; }
    public void setOtherSurname(String otherSurname) { this.otherSurname = otherSurname; }
    public String getOtherInitials() { return otherInitials; }
    public void setOtherInitials(String otherInitials) { this.otherInitials = otherInitials; }
    public String getOtherPhone() { return otherPhone; }
    public void setOtherPhone(String otherPhone) { this.otherPhone = otherPhone; }
    public String getOtherEmail() { return otherEmail; }
    public void setOtherEmail(String otherEmail) { this.otherEmail = otherEmail; }
    public String getOtherAddress() { return otherAddress; }
    public void setOtherAddress(String otherAddress) { this.otherAddress = otherAddress; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getCompanyCK() { return companyCK; }
    public void setCompanyCK(String companyCK) { this.companyCK = companyCK; }
    public String getCustomBg() { return customBg; }
    public void setCustomBg(String customBg) { this.customBg = customBg; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getIssuedDate() { return issuedDate; }
    public void setIssuedDate(Date issuedDate) { this.issuedDate = issuedDate; }
    public String getIssuedBy() { return issuedBy; }
    public void setIssuedBy(String issuedBy) { this.issuedBy = issuedBy; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}