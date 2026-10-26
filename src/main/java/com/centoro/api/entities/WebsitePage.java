package com.centoro.api.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "website_pages")
public class WebsitePage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "page_id", unique = true, nullable = false, length = 50)
    private String pageId;
    
    @Column(nullable = false)
    private String title;
    
    @Column(unique = true, nullable = false)
    private String slug;
    
    @Column(length = 50)
    private String template;
    
    @Column(name = "is_main")
    private Boolean isMain = false;
    
    @Column(name = "is_published")
    private Boolean isPublished = false;
    
    @Column(name = "published_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedAt;
    
    @Column(columnDefinition = "JSON")
    private String sections;
    
    @Column(columnDefinition = "JSON")
    private String settings;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPageId() { return pageId; }
    public void setPageId(String pageId) { this.pageId = pageId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getTemplate() { return template; }
    public void setTemplate(String template) { this.template = template; }
    public Boolean getIsMain() { return isMain; }
    public void setIsMain(Boolean isMain) { this.isMain = isMain; }
    public Boolean getIsPublished() { return isPublished; }
    public void setIsPublished(Boolean isPublished) { this.isPublished = isPublished; }
    public Date getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Date publishedAt) { this.publishedAt = publishedAt; }
    public String getSections() { return sections; }
    public void setSections(String sections) { this.sections = sections; }
    public String getSettings() { return settings; }
    public void setSettings(String settings) { this.settings = settings; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}