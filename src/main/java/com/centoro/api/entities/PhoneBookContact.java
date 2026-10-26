package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "phone_book_contacts")
public class PhoneBookContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "contact_id", unique = true, nullable = false, length = 50)
    private String contactId;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(nullable = false, length = 50)
    private String number;
    
    @Column(length = 50)
    private String category; // Client, Staff, Supplier, Emergency, Personal, Custom
    
    @Column(name = "speed_dial")
    private Integer speedDial;
    
    @Column(name = "client_id")
    private Long clientId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContactId() { return contactId; }
    public void setContactId(String contactId) { this.contactId = contactId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Integer getSpeedDial() { return speedDial; }
    public void setSpeedDial(Integer speedDial) { this.speedDial = speedDial; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}