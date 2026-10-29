package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "discount_id", unique = true, nullable = false, length = 50)
    private String discountId;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(length = 100)
    private String ref;
    
    // FIX: Changed from Long to String
    @Column(name = "client_id", length = 36)
    private String clientId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;
    
    @Column(length = 255)
    private String description;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double discountPct = 0.0;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double discountAmount = 0.0;
    
    @Column(length = 50)
    private String source = "quote";
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDiscountId() { return discountId; }
    public void setDiscountId(String discountId) { this.discountId = discountId; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getDiscountPct() { return discountPct; }
    public void setDiscountPct(Double discountPct) { this.discountPct = discountPct; }
    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
