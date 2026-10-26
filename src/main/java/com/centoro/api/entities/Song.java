package com.centoro.api.entities;
import jakarta.persistence.*;
@Entity
@Table(name = "songs")
public class Song {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String artist;
    @Column(nullable = false) private String title;
    @Column(nullable = false, length = 500) private String url;
    @Column(nullable = false, length = 500) private String video;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getArtist() { return artist; } public void setArtist(String artist) { this.artist = artist; }
    public String getTitle() { return title; } public void setTitle(String title) { this.title = title; }
    public String getUrl() { return url; } public void setUrl(String url) { this.url = url; }
    public String getVideo() { return video; } public void setVideo(String video) { this.video = video; }
}