package com.centoro.api.controller;

import com.centoro.api.entities.Project;
import com.centoro.api.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectsController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        projects.removeIf(p -> p.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of("success", true, "data", projects));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable String id) { // FIX: String
        Optional<Project> project = projectService.getProjectById(id);
        if (project.isEmpty() || project.get().getDeletedAt() != null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", project.get()));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getProjectsByClient(@PathVariable String clientId) { // FIX: String
        List<Project> projects = projectService.getProjectsByClient(clientId);
        return ResponseEntity.ok(Map.of("success", true, "data", projects));
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        Project saved = projectService.createProject(project);
        return ResponseEntity.ok(Map.of("success", true, "data", saved, "message", "Project created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable String id, @RequestBody Project project) { // FIX: String
        try {
            Project updated = projectService.updateProject(id, project);
            return ResponseEntity.ok(Map.of("success", true, "data", updated, "message", "Project updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id) { // FIX: String
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok(Map.of("success", true, "message", "Project deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
