package com.centoro.api.controller;

import com.centoro.api.dto.AnalyticsDTO;
import com.centoro.api.entities.Analytics;
import com.centoro.api.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping
    public ResponseEntity<?> getAllAnalytics() {
        List<Analytics> analytics = analyticsService.getAllAnalytics();
        List<AnalyticsDTO> analyticDTOs = analytics.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", analyticDTOs
        ));
    }

    @GetMapping("/countries")
    public ResponseEntity<?> getTopCountries() {
        List<Analytics> countries = analyticsService.getTopCountries();
        List<AnalyticsDTO> countryDTOs = countries.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", countryDTOs
        ));
    }

    @GetMapping("/interactions/recent")
    public ResponseEntity<?> getRecentInteractions() {
        List<Analytics> interactions = analyticsService.getRecentInteractions();
        List<AnalyticsDTO> interactionDTOs = interactions.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", interactionDTOs
        ));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getByType(@PathVariable String type) {
        List<Analytics> analytics = analyticsService.getByType(type);
        List<AnalyticsDTO> analyticDTOs = analytics.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", analyticDTOs
        ));
    }

    @PostMapping("/increment")
    public ResponseEntity<?> incrementAnalytic(@RequestBody Map<String, String> request) {
        String type = request.get("type");
        String label = request.get("label");
        
        if (type == null || label == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Type and label are required"
            ));
        }
        
        Analytics updated = analyticsService.incrementAnalytic(type, label);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(updated),
            "message", "Analytic incremented"
        ));
    }

    @PostMapping
    public ResponseEntity<?> createAnalytic(@RequestBody Analytics analytic) {
        Analytics saved = analyticsService.createAnalytic(analytic);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Analytic created successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnalytic(@PathVariable Long id) {
        if (!analyticsService.getAnalyticById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        analyticsService.deleteAnalytic(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Analytic deleted successfully"
        ));
    }

    private AnalyticsDTO convertToDTO(Analytics analytic) {
        AnalyticsDTO dto = new AnalyticsDTO();
        dto.setId(analytic.getId());
        dto.setAnalyticsId(analytic.getAnalyticsId());
        dto.setType(analytic.getType());
        dto.setLabel(analytic.getLabel());
        dto.setValue(analytic.getValue());
        dto.setCount(analytic.getCount());
        dto.setInteractions(analytic.getInteractions());
        dto.setCountry(analytic.getCountry());
        dto.setItem(analytic.getItem());
        dto.setDevice(analytic.getDevice());
        dto.setPercentage(analytic.getPercentage());
        dto.setTime(analytic.getTime());
        dto.setCreatedAt(analytic.getCreatedAt());
        return dto;
    }
}