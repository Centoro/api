package com.centoro.api.entities;
import jakarta.persistence.*;
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private String id;
    @Column(unique = true) private String userid;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private String surname;
    @Column(unique = true, nullable = false) private String email;
    @Column(nullable = false) private String password;
    private String contact;
    private String alternativeContact;
    private String province;
    private Integer age;
    private String artistName;
    private String winner;
    @Column(nullable = false) private boolean hasCompletedEntry = false;
    @Column(nullable = false) private String signupMethod = "EMAIL";
    public String getId() { return id; } public void setId(String id) { this.id = id; }
    public String getUserid() { return userid; } public void setUserid(String userid) { this.userid = userid; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; } public void setSurname(String surname) { this.surname = surname; }
    public String getEmail() { return email; } public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; } public void setPassword(String password) { this.password = password; }
    public String getContact() { return contact; } public void setContact(String contact) { this.contact = contact; }
    public String getAlternativeContact() { return alternativeContact; } public void setAlternativeContact(String alternativeContact) { this.alternativeContact = alternativeContact; }
    public String getProvince() { return province; } public void setProvince(String province) { this.province = province; }
    public Integer getAge() { return age; } public void setAge(Integer age) { this.age = age; }
    public String getArtistName() { return artistName; } public void setArtistName(String artistName) { this.artistName = artistName; }
    public String getWinner() { return winner; } public void setWinner(String winner) { this.winner = winner; }
    public boolean isHasCompletedEntry() { return hasCompletedEntry; } public void setHasCompletedEntry(boolean hasCompletedEntry) { this.hasCompletedEntry = hasCompletedEntry; }
    public String getSignupMethod() { return signupMethod; } public void setSignupMethod(String signupMethod) { this.signupMethod = signupMethod; }
}
