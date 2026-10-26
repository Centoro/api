package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "staff")
public class Staff {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "staff_id", unique = true, nullable = false, length = 50)
    private String staffId;
    
    @Column(nullable = false, length = 100)
    private String username;
    
    @Column(nullable = false, length = 255)
    private String email;
    
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    
    @Column(length = 50)
    private String phone;
    
    @Column(length = 50)
    private String mobile;
    
    @Column(length = 100)
    private String position;
    
    @Column(length = 100)
    private String department;
    
    @Column(name = "employee_number", unique = true, length = 50)
    private String employeeNumber;
    
    @Column(name = "id_number", length = 20)
    private String idNumber;
    
    @Column(name = "passport_number", length = 20)
    private String passportNumber;
    
    @Column(length = 100)
    private String nationality;
    
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    
    @Column(name = "hire_date")
    @Temporal(TemporalType.DATE)
    private Date hireDate;
    
    @Column(name = "termination_date")
    @Temporal(TemporalType.DATE)
    private Date terminationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StaffStatus status = StaffStatus.active;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StaffRole role = StaffRole.staff;
    
    @Column(columnDefinition = "JSON")
    private String permissions;
    
    @Column(columnDefinition = "DECIMAL(15,2)")
    private Double salary;
    
    @Column(name = "hourly_rate", columnDefinition = "DECIMAL(10,2)")
    private Double hourlyRate;
    
    @Column(name = "bank_name", length = 100)
    private String bankName;
    
    @Column(name = "bank_account", length = 50)
    private String bankAccount;
    
    @Column(name = "bank_branch_code", length = 10)
    private String bankBranchCode;
    
    @Column(name = "emergency_contact", length = 255)
    private String emergencyContact;
    
    @Column(name = "emergency_phone", length = 50)
    private String emergencyPhone;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "profile_image_url", columnDefinition = "TEXT")
    private String profileImageUrl;
    
    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    
    @Column(name = "can_reset_passwords")
    private Boolean canResetPasswords = false;
    
    @Column(name = "can_approve")
    private Boolean canApprove = false;
    
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
    
    private Integer age;
    
    @Temporal(TemporalType.DATE)
    private Date dob;
    
    // These fields don't exist in your database - REMOVE them or make them @Transient
    // religion, medical_conditions, next_of_kin, skills, pay_rate, pay_unit, 
    // contract_type, contract_start, contract_end, contract_signed, contract_signed_date, 
    // initials, surname, name
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    
    public enum StaffStatus {
        active, inactive, suspended, terminated
    }
    
    public enum StaffRole {
        admin, manager, staff, intern, contractor
    }
    
    // ============ GETTERS AND SETTERS ============
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getEmployeeNumber() { return employeeNumber; }
    public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }
    
    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
    
    public String getPassportNumber() { return passportNumber; }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }
    
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    
    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public Date getHireDate() { return hireDate; }
    public void setHireDate(Date hireDate) { this.hireDate = hireDate; }
    
    public Date getTerminationDate() { return terminationDate; }
    public void setTerminationDate(Date terminationDate) { this.terminationDate = terminationDate; }
    
    public StaffStatus getStatus() { return status; }
    public void setStatus(StaffStatus status) { this.status = status; }
    
    public StaffRole getRole() { return role; }
    public void setRole(StaffRole role) { this.role = role; }
    
    public String getPermissions() { return permissions; }
    public void setPermissions(String permissions) { this.permissions = permissions; }
    
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    
    public Double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }
    
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    
    public String getBankAccount() { return bankAccount; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }
    
    public String getBankBranchCode() { return bankBranchCode; }
    public void setBankBranchCode(String bankBranchCode) { this.bankBranchCode = bankBranchCode; }
    
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    
    public String getEmergencyPhone() { return emergencyPhone; }
    public void setEmergencyPhone(String emergencyPhone) { this.emergencyPhone = emergencyPhone; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
    
    public Date getLastLogin() { return lastLogin; }
    public void setLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }
    
    public Boolean getCanResetPasswords() { return canResetPasswords; }
    public void setCanResetPasswords(Boolean canResetPasswords) { this.canResetPasswords = canResetPasswords; }
    
    public Boolean getCanApprove() { return canApprove; }
    public void setCanApprove(Boolean canApprove) { this.canApprove = canApprove; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    
    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}