package com.centoro.api.controller;

import com.centoro.api.entities.WebsiteSlide;
import com.centoro.api.repositories.WebsiteSlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/website/slides")
@CrossOrigin(origins = "*")
public class WebsiteSlideController {

    @Autowired
    private WebsiteSlideRepository websiteSlideRepository;

    @GetMapping
    public ResponseEntity<?> getAllSlides() {
        List<WebsiteSlide> slides = websiteSlideRepository.findAll();
        slides.removeIf(s -> s.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", slides
        ));
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveSlides() {
        List<WebsiteSlide> slides = websiteSlideRepository.findByActiveTrue();
        slides.removeIf(s -> s.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", slides
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSlide(@PathVariable Long id) {
        Optional<WebsiteSlide> slideOpt = websiteSlideRepository.findById(id);
        if (slideOpt.isEmpty() || slideOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", slideOpt.get()
        ));
    }

    @PostMapping
    public ResponseEntity<?> createSlide(@RequestBody WebsiteSlide slide) {
        slide.setSlideId("SLD-" + String.format("%04d", System.currentTimeMillis() % 10000));
        slide.setCreatedAt(new Date());
        slide.setUpdatedAt(new Date());
        if (slide.getActive() == null) slide.setActive(true);
        
        WebsiteSlide saved = websiteSlideRepository.save(slide);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Slide created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSlide(@PathVariable Long id, @RequestBody WebsiteSlide slide) {
        Optional<WebsiteSlide> existingOpt = websiteSlideRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        WebsiteSlide existing = existingOpt.get();
        existing.setTitle(slide.getTitle());
        existing.setDesktopUrl(slide.getDesktopUrl());
        existing.setTabletUrl(slide.getTabletUrl());
        existing.setPhoneUrl(slide.getPhoneUrl());
        existing.setActive(slide.getActive());
        existing.setUpdatedAt(new Date());
        
        WebsiteSlide updated = websiteSlideRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Slide updated successfully"
        ));
    }

    @PostMapping("/{id}/toggle")
    public ResponseEntity<?> toggleSlide(@PathVariable Long id) {
        Optional<WebsiteSlide> slideOpt = websiteSlideRepository.findById(id);
        if (slideOpt.isEmpty() || slideOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        WebsiteSlide slide = slideOpt.get();
        slide.setActive(!slide.getActive());
        slide.setUpdatedAt(new Date());
        
        WebsiteSlide updated = websiteSlideRepository.save(slide);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Slide " + (updated.getActive() ? "shown" : "hidden") + " successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSlide(@PathVariable Long id) {
        Optional<WebsiteSlide> slideOpt = websiteSlideRepository.findById(id);
        if (slideOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        WebsiteSlide slide = slideOpt.get();
        slide.setDeletedAt(new Date());
        websiteSlideRepository.save(slide);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Slide deleted successfully"
        ));
    }
}