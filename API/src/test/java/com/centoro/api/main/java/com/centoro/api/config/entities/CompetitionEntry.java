package com.centoro.api.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "competition_entries")
public class CompetitionEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String category;
    private String artistName;
    private Integer age;
    private String status;
    private String userid;
    private String contactInfo;
    @Column(nullable = false) private String province;
    private LocalDateTime entryDate;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) private User user;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getCategory() { return category; } public void setCategory(String category) { this.category = category; }
    public String getArtistName() { return artistName; } public void setArtistName(String artistName) { this.artistName = artistName; }
    public Integer getAge() { return age; } public void setAge(Integer age) { this.age = age; }
    public String getStatus() { return status; } public void setStatus(String status) { this.status = status; }
    public String getUserid() { return userid; } public void setUserid(String userid) { this.userid = userid; }
    public String getContactInfo() { return contactInfo; } public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public String getProvince() { return province; } public void setProvince(String province) { this.province = province; }
    public LocalDateTime getEntryDate() { return entryDate; } public void setEntryDate(LocalDateTime entryDate) { this.entryDate = entryDate; }
    public User getUser() { return user; } public void setUser(User user) { this.user = user; }
}