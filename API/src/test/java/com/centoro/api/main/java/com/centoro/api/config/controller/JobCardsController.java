package com.centoro.api.controller;

import com.centoro.api.entities.JobCard;
import com.centoro.api.repositories.JobCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/jobcards")
@CrossOrigin(origins = "*")
public class JobCardsController {

    @Autowired
    private JobCardRepository jobCardRepository;

    @GetMapping
    public ResponseEntity<?> getAllJobCards() {
        List<JobCard> jobCards = jobCardRepository.findAll();
        jobCards.removeIf(j -> j.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", jobCards
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobCard(@PathVariable Long id) {
        Optional<JobCard> jobCard = jobCardRepository.findById(id);
        if (jobCard.isEmpty() || jobCard.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", jobCard.get()
        ));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getJobCardsByClient(@PathVariable Long clientId) {
        List<JobCard> jobCards = jobCardRepository.findByClientId(clientId);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", jobCards
        ));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getJobCardsByProject(@PathVariable Long projectId) {
        List<JobCard> jobCards = jobCardRepository.findByProjectId(projectId);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", jobCards
        ));
    }

    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<?> getJobCardsByAssignee(@PathVariable Long assigneeId) {
        List<JobCard> jobCards = jobCardRepository.findByAssigneeId(assigneeId);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", jobCards
        ));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getJobCardsByStatus(@PathVariable String status) {
        List<JobCard> jobCards = jobCardRepository.findByStatus(status);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", jobCards
        ));
    }

    @PostMapping
    public ResponseEntity<?> createJobCard(@RequestBody JobCard jobCard) {
        // Generate job card ID
        jobCard.setJobCardId("JC-" + String.format("%04d", System.currentTimeMillis() % 10000));
        jobCard.setLoggedAt(new Date());
        jobCard.setUpdatedAt(new Date());
        jobCard.setStatus("pending");
        
        if (jobCard.getHours() == null) jobCard.setHours(1.0);
        if (jobCard.getPriority() == null) jobCard.setPriority("medium");
        if (jobCard.getPeople() == null) jobCard.setPeople("1");
        if (jobCard.getLoggedBy() == null) jobCard.setLoggedBy("Admin");
        if (jobCard.getSource() == null) jobCard.setSource("walk-in");
        
        JobCard saved = jobCardRepository.save(jobCard);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Job card created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJobCard(@PathVariable Long id, @RequestBody JobCard jobCard) {
        Optional<JobCard> existingOpt = jobCardRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        JobCard existing = existingOpt.get();
        existing.setTitle(jobCard.getTitle());
        existing.setClientId(jobCard.getClientId());
        existing.setProjectId(jobCard.getProjectId());
        existing.setAssigneeId(jobCard.getAssigneeId());
        existing.setPriority(jobCard.getPriority());
        existing.setStatus(jobCard.getStatus());
        existing.setDueDate(jobCard.getDueDate());
        existing.setHours(jobCard.getHours());
        existing.setPeople(jobCard.getPeople());
        existing.setService(jobCard.getService());
        existing.setType(jobCard.getType());
        existing.setDescription(jobCard.getDescription());
        existing.setLockedBy(jobCard.getLockedBy());
        existing.setUpdatedBy(jobCard.getUpdatedBy());
        existing.setUpdatedAt(new Date());
        
        JobCard updated = jobCardRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Job card updated successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJobCard(@PathVariable Long id) {
        Optional<JobCard> jobCardOpt = jobCardRepository.findById(id);
        if (jobCardOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        JobCard jobCard = jobCardOpt.get();
        jobCard.setDeletedAt(new Date());
        jobCardRepository.save(jobCard);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Job card deleted successfully"
        ));
    }

    // ============ ADDITIONAL ENDPOINTS ============

    @GetMapping("/stats")
    public ResponseEntity<?> getJobCardStats() {
        List<JobCard> jobCards = jobCardRepository.findAll();
        jobCards.removeIf(j -> j.getDeletedAt() != null);
        
        long total = jobCards.size();
        long inProgress = jobCards.stream()
            .filter(j -> "in_progress".equals(j.getStatus()))
            .count();
        long completed = jobCards.stream()
            .filter(j -> "completed".equals(j.getStatus()))
            .count();
        long pending = jobCards.stream()
            .filter(j -> "pending".equals(j.getStatus()))
            .count();
        
        double totalHours = jobCards.stream()
            .mapToDouble(j -> j.getHours() != null ? j.getHours() : 0.0)
            .sum();
        
        Map<String, Object> stats = Map.of(
            "total", total,
            "inProgress", inProgress,
            "completed", completed,
            "pending", pending,
            "totalHours", totalHours
        );
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", stats
        ));
    }

    @GetMapping("/services")
    public ResponseEntity<?> getJobCardServices() {
        List<JobCard> jobCards = jobCardRepository.findAll();
        jobCards.removeIf(j -> j.getDeletedAt() != null);
        
        Set<String> services = new HashSet<>();
        for (JobCard job : jobCards) {
            if (job.getService() != null && !job.getService().isEmpty()) {
                services.add(job.getService());
            }
        }
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", new ArrayList<>(services)
        ));
    }

    @GetMapping("/types")
    public ResponseEntity<?> getJobCardTypes() {
        List<JobCard> jobCards = jobCardRepository.findAll();
        jobCards.removeIf(j -> j.getDeletedAt() != null);
        
        Set<String> types = new HashSet<>();
        for (JobCard job : jobCards) {
            if (job.getType() != null && !job.getType().isEmpty()) {
                types.add(job.getType());
            }
        }
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", new ArrayList<>(types)
        ));
    }

    @GetMapping("/assignee/{assigneeId}/stats")
    public ResponseEntity<?> getAssigneeStats(@PathVariable Long assigneeId) {
        List<JobCard> jobCards = jobCardRepository.findByAssigneeId(assigneeId);
        jobCards.removeIf(j -> j.getDeletedAt() != null);
        
        long total = jobCards.size();
        long completed = jobCards.stream()
            .filter(j -> "completed".equals(j.getStatus()))
            .count();
        long inProgress = jobCards.stream()
            .filter(j -> "in_progress".equals(j.getStatus()))
            .count();
        
        double totalHours = jobCards.stream()
            .mapToDouble(j -> j.getHours() != null ? j.getHours() : 0.0)
            .sum();
        
        Map<String, Object> stats = Map.of(
            "total", total,
            "completed", completed,
            "inProgress", inProgress,
            "totalHours", totalHours,
            "completionRate", total > 0 ? (double) completed / total * 100 : 0.0
        );
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", stats
        ));
    }

    @GetMapping("/overdue")
    public ResponseEntity<?> getOverdueJobCards() {
        List<JobCard> jobCards = jobCardRepository.findAll();
        jobCards.removeIf(j -> j.getDeletedAt() != null);
        
        Date today = new Date();
        List<JobCard> overdue = jobCards.stream()
            .filter(j -> !"completed".equals(j.getStatus()))
            .filter(j -> j.getDueDate() != null && j.getDueDate().before(today))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", overdue
        ));
    }

    @GetMapping("/project/{projectId}/stats")
    public ResponseEntity<?> getProjectJobStats(@PathVariable Long projectId) {
        List<JobCard> jobCards = jobCardRepository.findByProjectId(projectId);
        jobCards.removeIf(j -> j.getDeletedAt() != null);
        
        long total = jobCards.size();
        long completed = jobCards.stream()
            .filter(j -> "completed".equals(j.getStatus()))
            .count();
        long inProgress = jobCards.stream()
            .filter(j -> "in_progress".equals(j.getStatus()))
            .count();
        
        double totalHours = jobCards.stream()
            .mapToDouble(j -> j.getHours() != null ? j.getHours() : 0.0)
            .sum();
        
        Map<String, Object> stats = Map.of(
            "total", total,
            "completed", completed,
            "inProgress", inProgress,
            "totalHours", totalHours,
            "completionRate", total > 0 ? (double) completed / total * 100 : 0.0
        );
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", stats
        ));
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<?> updateJobStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        if (status == null || status.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Status is required"
            ));
        }
        
        Optional<JobCard> jobOpt = jobCardRepository.findById(id);
        if (jobOpt.isEmpty() || jobOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        JobCard job = jobOpt.get();
        job.setStatus(status);
        job.setUpdatedAt(new Date());
        job.setUpdatedBy(request.get("updatedBy") != null ? request.get("updatedBy") : "System");
        
        JobCard updated = jobCardRepository.save(job);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Job status updated successfully"
        ));
    }

    @PostMapping("/{id}/assign")
    public ResponseEntity<?> assignJob(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        Long assigneeId = request.get("assigneeId");
        if (assigneeId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Assignee ID is required"
            ));
        }
        
        Optional<JobCard> jobOpt = jobCardRepository.findById(id);
        if (jobOpt.isEmpty() || jobOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        JobCard job = jobOpt.get();
        job.setAssigneeId(assigneeId);
        job.setUpdatedAt(new Date());
        job.setStatus("in_progress");
        
        JobCard updated = jobCardRepository.save(job);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Job assigned successfully"
        ));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchJobCards(@RequestParam String q) {
        List<JobCard> jobCards = jobCardRepository.findAll();
        jobCards.removeIf(j -> j.getDeletedAt() != null);
        
        String query = q.toLowerCase();
        List<JobCard> results = jobCards.stream()
            .filter(j -> 
                (j.getTitle() != null && j.getTitle().toLowerCase().contains(query)) ||
                (j.getId() != null && j.getId().toString().contains(query)) ||
                (j.getType() != null && j.getType().toLowerCase().contains(query)) ||
                (j.getService() != null && j.getService().toLowerCase().contains(query)) ||
                (j.getStatus() != null && j.getStatus().toLowerCase().contains(query))
            )
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", results
        ));
    }
}