package com.centoro.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.centoro.api.entities.WebsitePage;
import com.centoro.api.repositories.WebsitePageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@RestController
@RequestMapping("/api/website/pages")
@CrossOrigin(origins = "*")
public class WebsitePagesController {
    
    @Autowired
    private WebsitePageRepository pageRepository;
    
    @GetMapping
    public ResponseEntity<?> getAllPages() {
        return ResponseEntity.ok(Map.of("success", true, "data", pageRepository.findAll()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPage(@PathVariable Long id) {
        return pageRepository.findById(id)
            .map(page -> ResponseEntity.ok(Map.of("success", true, "data", page)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createPage(@RequestBody WebsitePage page) {
        page.setCreatedAt(new Date());
        page.setUpdatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", pageRepository.save(page)));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePage(@PathVariable Long id, @RequestBody WebsitePage page) {
        return pageRepository.findById(id)
            .map(existing -> {
                existing.setTitle(page.getTitle());
                existing.setSlug(page.getSlug());
                existing.setSections(page.getSections());
                existing.setSettings(page.getSettings());
                existing.setIsPublished(page.getIsPublished());
                existing.setUpdatedAt(new Date());
                return ResponseEntity.ok(Map.of("success", true, "data", pageRepository.save(existing)));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{id}/publish")
    public ResponseEntity<?> publishPage(@PathVariable Long id) {
        return pageRepository.findById(id)
            .map(page -> {
                page.setIsPublished(true);
                page.setPublishedAt(new Date());
                page.setUpdatedAt(new Date());
                return ResponseEntity.ok(Map.of("success", true, "data", pageRepository.save(page)));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePage(@PathVariable Long id) {
        if (pageRepository.existsById(id)) {
            pageRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("success", true));
        }
        return ResponseEntity.notFound().build();
    }
}