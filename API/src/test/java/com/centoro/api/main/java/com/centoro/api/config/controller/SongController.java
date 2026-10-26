package com.centoro.api.controller;

import com.centoro.api.entities.Song;
import com.centoro.api.repositories.SongRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "*")
public class SongController {
    
    private final SongRepository songRepository;
    
    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    
    @GetMapping 
    public List<Song> getAllSongs() { 
        return songRepository.findAll(); 
    }
    
    @PostMapping 
    public Song createSong(@RequestBody Song song) { 
        return songRepository.save(song); 
    }
}