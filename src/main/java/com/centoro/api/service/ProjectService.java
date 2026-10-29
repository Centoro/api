package com.centoro.api.service;

import com.centoro.api.entities.Project;
import com.centoro.api.entities.ProjectMessage;
import com.centoro.api.repositories.ProjectRepository;
import com.centoro.api.repositories.ProjectMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired private ProjectRepository projectRepository;
    @Autowired private ProjectMessageRepository projectMessageRepository;

    public List<Project> getAllProjects() { return projectRepository.findAll(); }
    public Optional<Project> getProjectById(String id) { return projectRepository.findById(id); }
    public List<Project> getProjectsByClient(String clientId) { return projectRepository.findByClientId(clientId); }
    public List<Project> getProjectsByStatus(Project.ProjectStatus status) { return projectRepository.findByStatus(status); }

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

    public Project updateProject(String id, Project projectDetails) {
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

    public void deleteProject(String id) {
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

    public Map<String, Object> getProjectProfitability(String projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        return Map.of("projectId", projectId, "revenue", 0.0, "expenses", 0.0, "profit", 0.0);
    }

    // ===== Project Message Methods (the ones the compiler was missing) =====
    public List<ProjectMessage> getMessagesByProject(String projectId) {
        return projectMessageRepository.findByProjectId(projectId);
    }

    public Optional<ProjectMessage> getMessageById(Long id) {
        return projectMessageRepository.findById(id);
    }

    public ProjectMessage createMessage(ProjectMessage message) {
        message.setMessageId("PMG-" + String.format("%04d", System.currentTimeMillis() % 10000));
        message.setCreatedAt(new Date());
        message.setTime(new Date());
        return projectMessageRepository.save(message);
    }

    public void deleteMessage(Long id) {
        projectMessageRepository.deleteById(id);
    }
}
