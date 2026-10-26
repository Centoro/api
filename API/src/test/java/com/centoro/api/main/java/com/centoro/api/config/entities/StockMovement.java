package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stock_movements")
public class StockMovement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "movement_id", unique = true, nullable = false, length = 50)
    private String movementId;
    
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", insertable = false, updatable = false)
    private Inventory item;
    
    @Column(name = "item_name", nullable = false)
    private String itemName;
    
    @Column(nullable = false, length = 50)
    private String type;
    
    @Column(nullable = false)
    private Integer qty;
    
    @Column(length = 255)
    private String reference;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double costValue = 0.0;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getMovementId() { return movementId; }
    public void setMovementId(String movementId) { this.movementId = movementId; }
    
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    
    public Inventory getItem() { return item; }
    public void setItem(Inventory item) { this.item = item; }
    
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }
    
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    
    public Double getCostValue() { return costValue; }
    public void setCostValue(Double costValue) { this.costValue = costValue; }
    
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
