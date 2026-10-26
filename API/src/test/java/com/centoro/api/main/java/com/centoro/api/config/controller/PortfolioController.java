package com.centoro.api.controller;

import com.centoro.api.entities.PortfolioItem;
import com.centoro.api.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin(origins = "*")
public class PortfolioController {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @GetMapping
    public ResponseEntity<?> getAllItems() {
        List<PortfolioItem> items = portfolioRepository.findAll();
        items.removeIf(i -> i.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", items
        ));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getItemsByCategory(@PathVariable String category) {
        List<PortfolioItem> items = portfolioRepository.findByCategory(category);
        items.removeIf(i -> i.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", items
        ));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getItemsByStatus(@PathVariable String status) {
        List<PortfolioItem> items = portfolioRepository.findByStatus(status);
        items.removeIf(i -> i.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", items
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable Long id) {
        Optional<PortfolioItem> itemOpt = portfolioRepository.findById(id);
        if (itemOpt.isEmpty() || itemOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", itemOpt.get()
        ));
    }

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody PortfolioItem item) {
        item.setPortfolioId("PF-" + String.format("%04d", System.currentTimeMillis() % 10000));
        item.setCreatedAt(new Date());
        item.setUpdatedAt(new Date());
        if (item.getStatus() == null) item.setStatus("live");
        
        PortfolioItem saved = portfolioRepository.save(item);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Portfolio item created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody PortfolioItem item) {
        Optional<PortfolioItem> existingOpt = portfolioRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        PortfolioItem existing = existingOpt.get();
        existing.setTitle(item.getTitle());
        existing.setCategory(item.getCategory());
        existing.setSubcategory(item.getSubcategory());
        existing.setImageUrl(item.getImageUrl());
        existing.setStatus(item.getStatus());
        existing.setUpdatedAt(new Date());
        
        PortfolioItem updated = portfolioRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Portfolio item updated successfully"
        ));
    }

    @PostMapping("/{id}/toggle")
    public ResponseEntity<?> toggleItem(@PathVariable Long id) {
        Optional<PortfolioItem> itemOpt = portfolioRepository.findById(id);
        if (itemOpt.isEmpty() || itemOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        PortfolioItem item = itemOpt.get();
        item.setStatus("hidden".equals(item.getStatus()) ? "live" : "hidden");
        item.setUpdatedAt(new Date());
        
        PortfolioItem updated = portfolioRepository.save(item);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Portfolio item " + ("live".equals(updated.getStatus()) ? "shown" : "hidden") + " successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        Optional<PortfolioItem> itemOpt = portfolioRepository.findById(id);
        if (itemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        PortfolioItem item = itemOpt.get();
        item.setDeletedAt(new Date());
        portfolioRepository.save(item);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Portfolio item deleted successfully"
        ));
    }
}