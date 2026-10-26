package com.centoro.api.controller;

import com.centoro.api.entities.WebsiteNews;
import com.centoro.api.repositories.WebsiteNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/website/news")
@CrossOrigin(origins = "*")
public class WebsiteNewsController {

    @Autowired
    private WebsiteNewsRepository websiteNewsRepository;

    @GetMapping
    public ResponseEntity<?> getAllNews() {
        List<WebsiteNews> news = websiteNewsRepository.findAll();
        news.removeIf(n -> n.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", news
        ));
    }

    @GetMapping("/published")
    public ResponseEntity<?> getPublishedNews() {
        List<WebsiteNews> news = websiteNewsRepository.findByPublishedTrue();
        news.removeIf(n -> n.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", news
        ));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getNewsByCategory(@PathVariable String category) {
        List<WebsiteNews> news = websiteNewsRepository.findByCategory(category);
        news.removeIf(n -> n.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", news
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNews(@PathVariable Long id) {
        Optional<WebsiteNews> newsOpt = websiteNewsRepository.findById(id);
        if (newsOpt.isEmpty() || newsOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", newsOpt.get()
        ));
    }

    @PostMapping
    public ResponseEntity<?> createNews(@RequestBody WebsiteNews news) {
        news.setNewsId("NWS-" + String.format("%04d", System.currentTimeMillis() % 10000));
        news.setCreatedAt(new Date());
        news.setUpdatedAt(new Date());
        if (news.getDate() == null) news.setDate(new Date());
        if (news.getPublished() == null) news.setPublished(true);
        if (news.getImageSize() == null) news.setImageSize("wide");
        
        WebsiteNews saved = websiteNewsRepository.save(news);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "News article created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id, @RequestBody WebsiteNews news) {
        Optional<WebsiteNews> existingOpt = websiteNewsRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        WebsiteNews existing = existingOpt.get();
        existing.setTitle(news.getTitle());
        existing.setCategory(news.getCategory());
        existing.setBody(news.getBody());
        existing.setImageUrl(news.getImageUrl());
        existing.setImageSize(news.getImageSize());
        existing.setPublished(news.getPublished());
        existing.setDate(news.getDate());
        existing.setUpdatedAt(new Date());
        
        WebsiteNews updated = websiteNewsRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "News article updated successfully"
        ));
    }

    @PostMapping("/{id}/toggle")
    public ResponseEntity<?> toggleNews(@PathVariable Long id) {
        Optional<WebsiteNews> newsOpt = websiteNewsRepository.findById(id);
        if (newsOpt.isEmpty() || newsOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        WebsiteNews news = newsOpt.get();
        news.setPublished(!news.getPublished());
        news.setUpdatedAt(new Date());
        
        WebsiteNews updated = websiteNewsRepository.save(news);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "News " + (updated.getPublished() ? "published" : "unpublished") + " successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        Optional<WebsiteNews> newsOpt = websiteNewsRepository.findById(id);
        if (newsOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        WebsiteNews news = newsOpt.get();
        news.setDeletedAt(new Date());
        websiteNewsRepository.save(news);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "News article deleted successfully"
        ));
    }
}