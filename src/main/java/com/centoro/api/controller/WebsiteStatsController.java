package com.centoro.api.controller;

import com.centoro.api.dto.WebsiteStatsDTO;
import com.centoro.api.entities.WebsiteStats;
import com.centoro.api.service.WebsiteStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/website/stats")
@CrossOrigin(origins = "*")
public class WebsiteStatsController {

    @Autowired
    private WebsiteStatsService websiteStatsService;

    @GetMapping
    public ResponseEntity<?> getAllStats() {
        List<WebsiteStats> stats = websiteStatsService.getAllStats();
        List<WebsiteStatsDTO> statsDTOs = stats.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", statsDTOs
        ));
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestStats() {
        Optional<WebsiteStats> statsOpt = websiteStatsService.getLatestStats();
        if (statsOpt.isEmpty()) {
            // Return default stats
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", Map.of(
                    "visitors", 0,
                    "downloads", 0,
                    "conversionRate", 0.0,
                    "topCountry", "—"
                )
            ));
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(statsOpt.get())
        ));
    }

    @GetMapping("/totals")
    public ResponseEntity<?> getTotals() {
        Long visitors = websiteStatsService.getTotalVisitors();
        Long downloads = websiteStatsService.getTotalDownloads();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", Map.of(
                "totalVisitors", visitors != null ? visitors : 0,
                "totalDownloads", downloads != null ? downloads : 0
            )
        ));
    }

    @GetMapping("/range")
    public ResponseEntity<?> getStatsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        List<WebsiteStats> stats = websiteStatsService.getStatsByDateRange(start, end);
        List<WebsiteStatsDTO> statsDTOs = stats.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", statsDTOs
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStats(@PathVariable Long id) {
        Optional<WebsiteStats> statsOpt = websiteStatsService.getStatsById(id);
        if (statsOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(statsOpt.get())
        ));
    }

    @PostMapping
    public ResponseEntity<?> createStats(@RequestBody WebsiteStats stats) {
        WebsiteStats saved = websiteStatsService.createStats(stats);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Website stats created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStats(@PathVariable Long id, @RequestBody WebsiteStats stats) {
        try {
            WebsiteStats updated = websiteStatsService.updateStats(id, stats);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Website stats updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/visitor")
    public ResponseEntity<?> incrementVisitor(@PathVariable Long id) {
        try {
            WebsiteStats updated = websiteStatsService.incrementVisitors(id);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Visitor count incremented"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/download")
    public ResponseEntity<?> incrementDownload(@PathVariable Long id) {
        try {
            WebsiteStats updated = websiteStatsService.incrementDownloads(id);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Download count incremented"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStats(@PathVariable Long id) {
        if (!websiteStatsService.getStatsById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        websiteStatsService.deleteStats(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Website stats deleted successfully"
        ));
    }

    private WebsiteStatsDTO convertToDTO(WebsiteStats stats) {
        WebsiteStatsDTO dto = new WebsiteStatsDTO();
        dto.setId(stats.getId());
        dto.setStatsId(stats.getStatsId());
        dto.setVisitors(stats.getVisitors());
        dto.setDownloads(stats.getDownloads());
        dto.setConversionRate(stats.getConversionRate());
        dto.setTopCountry(stats.getTopCountry());
        dto.setDate(stats.getDate());
        dto.setCreatedAt(stats.getCreatedAt());
        dto.setUpdatedAt(stats.getUpdatedAt());
        return dto;
    }
}