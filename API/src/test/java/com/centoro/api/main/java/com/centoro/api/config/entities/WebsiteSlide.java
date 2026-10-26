package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "website_slides")
public class WebsiteSlide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "slide_id", unique = true, nullable = false, length = 50)
    private String slideId;
    
    @Column(nullable = false)
    private String title;
    
    @Column(name = "desktop_url", length = 500)
    private String desktopUrl;
    
    @Column(name = "tablet_url", length = 500)
    private String tabletUrl;
    
    @Column(name = "phone_url", length = 500)
    private String phoneUrl;
    
    @Column(name = "active")
    private Boolean active = true;
    
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
    public String getSlideId() { return slideId; }
    public void setSlideId(String slideId) { this.slideId = slideId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDesktopUrl() { return desktopUrl; }
    public void setDesktopUrl(String desktopUrl) { this.desktopUrl = desktopUrl; }
    public String getTabletUrl() { return tabletUrl; }
    public void setTabletUrl(String tabletUrl) { this.tabletUrl = tabletUrl; }
    public String getPhoneUrl() { return phoneUrl; }
    public void setPhoneUrl(String phoneUrl) { this.phoneUrl = phoneUrl; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}