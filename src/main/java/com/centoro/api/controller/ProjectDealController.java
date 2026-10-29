package com.centoro.api.controller;

import com.centoro.api.entities.ProjectDeal;
import com.centoro.api.repositories.ProjectDealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects/deals")
@CrossOrigin(origins = "*")
public class ProjectDealController {

    @Autowired
    private ProjectDealRepository projectDealRepository;

    @GetMapping
    public ResponseEntity<?> getAllDeals() {
        List<ProjectDeal> deals = projectDealRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", deals));
    }

    @GetMapping("/stage/{stage}")
    public ResponseEntity<?> getDealsByStage(@PathVariable String stage) {
        List<ProjectDeal> deals = projectDealRepository.findByStage(stage);
        return ResponseEntity.ok(Map.of("success", true, "data", deals));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeal(@PathVariable Long id) {
        Optional<ProjectDeal> deal = projectDealRepository.findById(id);
        if (deal.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", deal.get()));
    }

    @PostMapping
    public ResponseEntity<?> createDeal(@RequestBody ProjectDeal deal) {
        deal.setDealId("DEAL-" + String.format("%04d", System.currentTimeMillis() % 10000));
        deal.setCreatedAt(new Date());
        deal.setUpdatedAt(new Date());
        if (deal.getStage() == null) deal.setStage("Lead");
        if (deal.getScore() == null) deal.setScore(50);
        if (deal.getValue() == null) deal.setValue(0.0);
        ProjectDeal saved = projectDealRepository.save(deal);
        return ResponseEntity.ok(Map.of("success", true, "data", saved, "message", "Deal created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeal(@PathVariable Long id, @RequestBody ProjectDeal deal) {
        Optional<ProjectDeal> existingOpt = projectDealRepository.findById(id);
        if (existingOpt.isEmpty()) return ResponseEntity.notFound().build();
        ProjectDeal existing = existingOpt.get();
        existing.setName(deal.getName());
        existing.setClientId(deal.getClientId());
        existing.setValue(deal.getValue());
        existing.setScore(deal.getScore());
        existing.setStage(deal.getStage());
        existing.setUpdatedAt(new Date());
        ProjectDeal updated = projectDealRepository.save(existing);
        return ResponseEntity.ok(Map.of("success", true, "data", updated, "message", "Deal updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeal(@PathVariable Long id) {
        if (!projectDealRepository.existsById(id)) return ResponseEntity.notFound().build();
        projectDealRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Deal deleted successfully"));
    }
}
