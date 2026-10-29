package com.centoro.api.controller;

import com.centoro.api.entities.ProjectAutomation;
import com.centoro.api.repositories.ProjectAutomationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects/automations")
@CrossOrigin(origins = "*")
public class ProjectAutomationController {

    @Autowired
    private ProjectAutomationRepository automationRepository;

    @GetMapping
    public ResponseEntity<?> getAllAutomations() {
        List<ProjectAutomation> automations = automationRepository.findAll();
        return ResponseEntity.ok(Map.of("success", true, "data", automations));
    }

    @GetMapping("/trigger/{trigger}")
    public ResponseEntity<?> getAutomationsByTrigger(@PathVariable String trigger) {
        List<ProjectAutomation> automations = automationRepository.findByTrigger(trigger);
        return ResponseEntity.ok(Map.of("success", true, "data", automations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAutomation(@PathVariable Long id) {
        Optional<ProjectAutomation> automation = automationRepository.findById(id);
        if (automation.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", automation.get()));
    }

    @PostMapping
    public ResponseEntity<?> createAutomation(@RequestBody ProjectAutomation automation) {
        automation.setAutomationId("AUTO-" + String.format("%04d", System.currentTimeMillis() % 10000));
        automation.setCreatedAt(new Date());
        ProjectAutomation saved = automationRepository.save(automation);
        return ResponseEntity.ok(Map.of("success", true, "data", saved, "message", "Automation rule created successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAutomation(@PathVariable Long id) {
        if (!automationRepository.existsById(id)) return ResponseEntity.notFound().build();
        automationRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Automation rule deleted successfully"));
    }
}
