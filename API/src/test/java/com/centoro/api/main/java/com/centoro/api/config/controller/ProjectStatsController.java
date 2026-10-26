package com.centoro.api.controller;

import com.centoro.api.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/projects/stats")
@CrossOrigin(origins = "*")
public class ProjectStatsController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getProjectStats() {
        Map<String, Object> stats = projectService.getProjectStats();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", stats
        ));
    }

    @GetMapping("/profit/{projectId}")
    public ResponseEntity<?> getProjectProfitability(@PathVariable Long projectId) {
        try {
            Map<String, Object> profitability = projectService.getProjectProfitability(projectId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", profitability
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}