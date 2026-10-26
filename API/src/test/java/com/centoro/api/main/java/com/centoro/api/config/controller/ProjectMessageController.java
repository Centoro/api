package com.centoro.api.controller;

import com.centoro.api.entities.ProjectMessage;
import com.centoro.api.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects/messages")
@CrossOrigin(origins = "*")
public class ProjectMessageController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getMessagesByProject(@PathVariable Long projectId) {
        List<ProjectMessage> messages = projectService.getMessagesByProject(projectId);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", messages
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMessage(@PathVariable Long id) {
        Optional<ProjectMessage> message = projectService.getMessageById(id);
        if (message.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", message.get()
        ));
    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody ProjectMessage message) {
        if (message.getProjectId() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Project ID is required"
            ));
        }
        ProjectMessage saved = projectService.createMessage(message);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Message sent successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        if (!projectService.getMessageById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        projectService.deleteMessage(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Message deleted successfully"
        ));
    }
}