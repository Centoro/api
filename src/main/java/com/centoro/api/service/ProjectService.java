package com.centoro.api.service;

import com.centoro.api.entities.Project;
import com.centoro.api.repositories.ProjectRepository;
import com.centoro.api.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Project> getAllProjects() { return projectRepository.findAll(); }

    public Optional<Project> getProjectById(String id) { return projectRepository.findById(id); } // FIX: String

    public List<Project> getProjectsByClient(String clientId) { return projectRepository.findByClientId(clientId); } // FIX: String

    public Project createProject(Project project) {
        project.setProjectId("PRJ-" + String.format("%04d", System.currentTimeMillis() % 10000));
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());
        if (project.getStatus() == null) project.setStatus(Project.ProjectStatus.planning);
        if (project.getBudget() == null) project.setBudget(0.0);
        if (project.getSpent() == null) project.setSpent(0.0);
        if (project.getProgress() == null) project.setProgress(0);
        return projectRepository.save(project);
    }

    public Project updateProject(String id, Project projectDetails) { // FIX: String
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        project.setName(projectDetails.getName());
        project.setClientId(projectDetails.getClientId());
        project.setService(projectDetails.getService());
        project.setBudget(projectDetails.getBudget());
        project.setSpent(projectDetails.getSpent());
        project.setProgress(projectDetails.getProgress());
        project.setStatus(projectDetails.getStatus());
        project.setAssignees(projectDetails.getAssignees());
        project.setDueDate(projectDetails.getDueDate());
        project.setPriority(projectDetails.getPriority());
        project.setDescription(projectDetails.getDescription());
        project.setUpdatedAt(new Date());
        return projectRepository.save(project);
    }

    public void deleteProject(String id) { // FIX: String
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        project.setDeletedAt(new Date());
        projectRepository.save(project);
    }

    public Map<String, Object> getProjectStats() {
        List<Project> projects = projectRepository.findAll();
        long total = projects.size();
        long completed = projects.stream().filter(p -> p.getStatus() == Project.ProjectStatus.completed).count();
        long active = projects.stream().filter(p -> p.getStatus() == Project.ProjectStatus.in_progress).count();
        return Map.of("total", total, "completed", completed, "active", active);
    }

    public Map<String, Object> getProjectProfitability(String projectId) { // FIX: String
        Project project = projectRepository.findById(projectId).orElseThrow();
        // Example logic - adjust based on your actual financial setup
        Double revenue = invoiceRepository.findByClientId(project.getClientId()).stream()
                .filter(i -> i.getProjectId() != null && i.getProjectId().equals(projectId))
                .mapToDouble(i -> i.getTotal() != null ? i.getTotal() : 0.0)
                .sum();
        Double expenses = 0.0; // Placeholder
        Map<String, Object> result = Map.of("projectId", projectId, "revenue", revenue, "expenses", expenses, "profit", revenue - expenses);
        return result;
    }
}
