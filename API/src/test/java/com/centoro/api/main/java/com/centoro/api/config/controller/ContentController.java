package com.centoro.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.centoro.api.entities.Content;
import com.centoro.api.repositories.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.*;
import java.nio.file.*;

@RestController
@RequestMapping("/api/content")
@CrossOrigin(origins = "*")
public class ContentController {
    
    @Autowired
    private ContentRepository contentRepository;
    
    @Value("${upload.dir:uploads}")
    private String uploadDir;
    
    @GetMapping
    public ResponseEntity<?> getAllContent() {
        return ResponseEntity.ok(Map.of("success", true, "data", contentRepository.findAll()));
    }
    
    @GetMapping("/{type}")
    public ResponseEntity<?> getContentByType(@PathVariable String type) {
        return ResponseEntity.ok(Map.of("success", true, "data", contentRepository.findByType(type)));
    }
    
    @GetMapping("/featured")
    public ResponseEntity<?> getFeaturedContent() {
        return ResponseEntity.ok(Map.of("success", true, "data", contentRepository.findByIsFeaturedTrue()));
    }
    
    @PostMapping("/music")
    public ResponseEntity<?> uploadMusic(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "artist", required = false) String artist,
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "bpm", required = false) Integer bpm,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "album", required = false) String album) {
        
        try {
            // Create upload directory if not exists
            Path uploadPath = Paths.get(uploadDir, "music");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Save file
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Create content record
            Content content = new Content();
            content.setContentId("CTN-" + System.currentTimeMillis());
            content.setType("music");
            content.setTitle(title != null ? title : file.getOriginalFilename());
            content.setArtist(artist);
            content.setGenre(genre);
            content.setBpm(bpm);
            content.setMusicKey(key);
            content.setAlbum(album);
            content.setFileUrl("/uploads/music/" + fileName);
            content.setFileSize(file.getSize());
            content.setFileType(file.getContentType());
            content.setUploadedBy("System");
            content.setCreatedAt(new Date());
            content.setUpdatedAt(new Date());
            content.setPublished(true);
            
            Content saved = contentRepository.save(content);
            return ResponseEntity.ok(Map.of("success", true, "data", saved));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file,
                                         @RequestParam(value = "title", required = false) String title) {
        try {
            Path uploadPath = Paths.get(uploadDir, "images");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            Content content = new Content();
            content.setContentId("CTN-" + System.currentTimeMillis());
            content.setType("image");
            content.setTitle(title != null ? title : file.getOriginalFilename());
            content.setFileUrl("/uploads/images/" + fileName);
            content.setThumbnailUrl("/uploads/images/" + fileName);
            content.setFileSize(file.getSize());
            content.setFileType(file.getContentType());
            content.setUploadedBy("System");
            content.setCreatedAt(new Date());
            content.setUpdatedAt(new Date());
            content.setPublished(true);
            
            Content saved = contentRepository.save(content);
            return ResponseEntity.ok(Map.of("success", true, "data", saved));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContent(@PathVariable Long id) {
        if (contentRepository.existsById(id)) {
            contentRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("success", true));
        }
        return ResponseEntity.notFound().build();
    }
}
