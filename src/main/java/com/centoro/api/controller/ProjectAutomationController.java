package com.centoro.api.controller;

import com.centoro.api.entities.ProjectAutomation;
import com.centoro.api.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects/automations")
@CrossOrigin(origins = "*")
public class ProjectAutomationController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getAllAutomations() {
        List<ProjectAutomation> automations = projectService.getAllAutomations();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", automations
        ));
    }

    @GetMapping("/trigger/{trigger}")
    public ResponseEntity<?> getAutomationsByTrigger(@PathVariable String trigger) {
        List<ProjectAutomation> automations = projectService.getAutomationsByTrigger(trigger);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", automations
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAutomation(@PathVariable Long id) {
        Optional<ProjectAutomation> automation = projectService.getAutomationById(id);
        if (automation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", automation.get()
        ));
    }

    @PostMapping
    public ResponseEntity<?> createAutomation(@RequestBody ProjectAutomation automation) {
        ProjectAutomation saved = projectService.createAutomation(automation);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Automation rule created successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAutomation(@PathVariable Long id) {
        if (!projectService.getAutomationById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        projectService.deleteAutomation(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Automation rule deleted successfully"
        ));
    }
}