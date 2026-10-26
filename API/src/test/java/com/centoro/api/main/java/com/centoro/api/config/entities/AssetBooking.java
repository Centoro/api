package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "asset_bookings")
public class AssetBooking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "booking_id", unique = true, nullable = false, length = 50)
    private String bookingId;
    
    @Column(name = "asset_id", nullable = false)
    private Long assetId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", insertable = false, updatable = false)
    private Asset asset;
    
    @Column(name = "staff_id", nullable = false)
    private Long staffId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", insertable = false, updatable = false)
    private Staff staff;
    
    @Column(name = "out_date")
    @Temporal(TemporalType.DATE)
    private Date outDate;
    
    @Column(name = "due_back")
    @Temporal(TemporalType.DATE)
    private Date dueBack;
    
    @Column(name = "returned_date")
    @Temporal(TemporalType.DATE)
    private Date returnedDate;
    
    @Column(length = 255)
    private String note;
    
    @Column(name = "booked_by", length = 255)
    private String bookedBy;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    
    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    
    public Asset getAsset() { return asset; }
    public void setAsset(Asset asset) { this.asset = asset; }
    
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }
    
    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }
    
    public Date getOutDate() { return outDate; }
    public void setOutDate(Date outDate) { this.outDate = outDate; }
    
    public Date getDueBack() { return dueBack; }
    public void setDueBack(Date dueBack) { this.dueBack = dueBack; }
    
    public Date getReturnedDate() { return returnedDate; }
    public void setReturnedDate(Date returnedDate) { this.returnedDate = returnedDate; }
    
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    
    public String getBookedBy() { return bookedBy; }
    public void setBookedBy(String bookedBy) { this.bookedBy = bookedBy; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}