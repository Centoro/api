package com.centoro.api.controller;

import com.centoro.api.entities.Project;
import com.centoro.api.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectsController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        projects.removeIf(p -> p.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", projects
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty() || project.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", project.get()
        ));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getProjectsByClient(@PathVariable Long clientId) {
        List<Project> projects = projectRepository.findActiveByClientId(clientId);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", projects
        ));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getProjectsByStatus(@PathVariable String status) {
        Project.ProjectStatus projectStatus;
        try {
            projectStatus = Project.ProjectStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Invalid status value"
            ));
        }
        List<Project> projects = projectRepository.findByStatus(projectStatus);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", projects
        ));
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        // Generate project ID
        project.setProjectId("PRJ-" + String.format("%04d", System.currentTimeMillis() % 10000));
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());
        project.setProgress(0);
        project.setSpent(0.0);
        
        if (project.getStatus() == null) {
            project.setStatus(Project.ProjectStatus.planning);
        }
        if (project.getBudget() == null) project.setBudget(0.0);
        
        Project saved = projectRepository.save(project);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Project created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Project project) {
        Optional<Project> existingOpt = projectRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        Project existing = existingOpt.get();
        existing.setName(project.getName());
        existing.setClientId(project.getClientId());
        existing.setService(project.getService());
        existing.setBudget(project.getBudget());
        existing.setSpent(project.getSpent());
        existing.setProgress(project.getProgress());
        existing.setStatus(project.getStatus());
        existing.setAssignees(project.getAssignees());
        existing.setDueDate(project.getDueDate());
        existing.setPriority(project.getPriority());
        existing.setDescription(project.getDescription());
        existing.setUpdatedAt(new Date());
        
        Project updated = projectRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Project updated successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Project project = projectOpt.get();
        project.setDeletedAt(new Date());
        projectRepository.save(project);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Project deleted successfully"
        ));
    }
}