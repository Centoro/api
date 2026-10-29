package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "clients")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;
    
    @Column(name = "client_id", unique = true, nullable = false, length = 50)
    private String clientId;
    
    @Column(length = 20)
    private String code;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "first_name", length = 100)
    private String firstName;
    
    @Column(name = "middle_name", length = 100)
    private String middleName;
    
    @Column(length = 100)
    private String surname;
    
    @Column(length = 10)
    private String initials;
    
    @Column(name = "id_number", length = 50)
    private String idNumber;
    
    @Temporal(TemporalType.DATE)
    private Date dob;
    
    @Column(length = 255)
    private String email;
    
    @Column(length = 50)
    private String phone;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    @Column(length = 255)
    private String website;
    
    @Column(name = "company_name", length = 255)
    private String companyName;
    
    @Column(name = "company_ck", length = 50)
    private String companyCK;
    
    @Column(name = "total_spent", columnDefinition = "DECIMAL(18,2) DEFAULT 0.00")
    private Double totalSpent = 0.0;
    
    @Column(name = "projects_count")
    private Integer projectsCount = 0;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClientStatus status = ClientStatus.active;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    
    public enum ClientStatus {
        active, inactive
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getInitials() { return initials; }
    public void setInitials(String initials) { this.initials = initials; }
    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanyCK() { return companyCK; }
    public void setCompanyCK(String companyCK) { this.companyCK = companyCK; }
    public Double getTotalSpent() { return totalSpent; }
    public void setTotalSpent(Double totalSpent) { this.totalSpent = totalSpent; }
    public Integer getProjectsCount() { return projectsCount; }
    public void setProjectsCount(Integer projectsCount) { this.projectsCount = projectsCount; }
    public ClientStatus getStatus() { return status; }
    public void setStatus(ClientStatus status) { this.status = status; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}
