package com.centoro.api.controller;

import com.centoro.api.entities.ProjectDeal;
import com.centoro.api.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects/deals")
@CrossOrigin(origins = "*")
public class ProjectDealController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getAllDeals() {
        List<ProjectDeal> deals = projectService.getAllDeals();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", deals
        ));
    }

    @GetMapping("/stage/{stage}")
    public ResponseEntity<?> getDealsByStage(@PathVariable String stage) {
        List<ProjectDeal> deals = projectService.getDealsByStage(stage);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", deals
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeal(@PathVariable Long id) {
        Optional<ProjectDeal> deal = projectService.getDealById(id);
        if (deal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", deal.get()
        ));
    }

    @PostMapping
    public ResponseEntity<?> createDeal(@RequestBody ProjectDeal deal) {
        ProjectDeal saved = projectService.createDeal(deal);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Deal created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeal(@PathVariable Long id, @RequestBody ProjectDeal deal) {
        try {
            ProjectDeal updated = projectService.updateDeal(id, deal);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", updated,
                "message", "Deal updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeal(@PathVariable Long id) {
        if (!projectService.getDealById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        projectService.deleteDeal(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Deal deleted successfully"
        ));
    }
}