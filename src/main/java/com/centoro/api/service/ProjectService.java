package com.centoro.api.service;

import com.centoro.api.entities.*;
import com.centoro.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectDealRepository dealRepository;
    
    @Autowired
    private ProjectAutomationRepository automationRepository;
    
    @Autowired
    private ProjectMessageRepository messageRepository;
    
    @Autowired
    private JobCardRepository jobCardRepository;
    
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private ExpenseRepository expenseRepository;

    // ============ PROJECTS ============
    
    @Transactional
    public Project createProject(Project project) {
        project.setProjectId("PRJ-" + String.format("%04d", System.currentTimeMillis() % 10000));
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());
        if (project.getProgress() == null) project.setProgress(0);
        if (project.getSpent() == null) project.setSpent(0.0);
        if (project.getStatus() == null) project.setStatus(Project.ProjectStatus.planning);
        return projectRepository.save(project);
    }
    
    public List<Project> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        projects.removeIf(p -> p.getDeletedAt() != null);
        return projects;
    }
    
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
    
    public List<Project> getProjectsByClient(Long clientId) {
        return projectRepository.findActiveByClientId(clientId);
    }
    
    public List<Project> getProjectsByStatus(Project.ProjectStatus status) {
        return projectRepository.findByStatus(status);
    }
    
    public Map<String, Object> getProjectStats() {
        List<Project> projects = getAllProjects();
        long total = projects.size();
        long planning = projects.stream().filter(p -> p.getStatus() == Project.ProjectStatus.planning).count();
        long inProgress = projects.stream().filter(p -> p.getStatus() == Project.ProjectStatus.in_progress).count();
        long completed = projects.stream().filter(p -> p.getStatus() == Project.ProjectStatus.completed).count();
        
        double totalBudget = projects.stream().mapToDouble(p -> p.getBudget() != null ? p.getBudget() : 0.0).sum();
        double totalSpent = projects.stream().mapToDouble(p -> p.getSpent() != null ? p.getSpent() : 0.0).sum();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("planning", planning);
        stats.put("inProgress", inProgress);
        stats.put("completed", completed);
        stats.put("totalBudget", totalBudget);
        stats.put("totalSpent", totalSpent);
        
        return stats;
    }
    
    public Map<String, Object> getProjectProfitability(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        
        double invoiced = invoiceRepository.findByProjectId(projectId).stream()
            .mapToDouble(i -> i.getTotal() != null ? i.getTotal() : 0.0)
            .sum();
        
        double expenses = expenseRepository.findByProjectId(projectId).stream()
            .filter(e -> e.getStatus() == Expense.ExpenseStatus.approved)
            .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0.0)
            .sum();
        
        double labour = jobCardRepository.findByProjectId(projectId).stream()
            .mapToDouble(j -> {
                if (j.getAssigneeId() == null) return 0.0;
                // Simplified: assume hourly rate from staff salary
                return j.getHours() != null ? j.getHours() * 100 : 0.0;
            })
            .sum();
        
        double profit = invoiced - expenses - labour;
        double margin = invoiced > 0 ? (profit / invoiced) * 100 : 0;
        
        Map<String, Object> result = new HashMap<>();
        result.put("projectId", projectId);
        result.put("name", project.getName());
        result.put("budget", project.getBudget());
        result.put("invoiced", invoiced);
        result.put("expenses", expenses);
        result.put("labour", labour);
        result.put("profit", profit);
        result.put("margin", margin);
        
        return result;
    }
    
    @Transactional
    public Project updateProject(Long id, Project project) {
        Project existing = projectRepository.findById(id).orElseThrow();
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
        return projectRepository.save(existing);
    }
    
    @Transactional
    public void softDeleteProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow();
        project.setDeletedAt(new Date());
        projectRepository.save(project);
    }

    // ============ DEALS ============
    
    @Transactional
    public ProjectDeal createDeal(ProjectDeal deal) {
        deal.setDealId("DEAL-" + String.format("%04d", System.currentTimeMillis() % 10000));
        deal.setCreatedAt(new Date());
        deal.setUpdatedAt(new Date());
        if (deal.getScore() == null) deal.setScore(50);
        if (deal.getValue() == null) deal.setValue(0.0);
        if (deal.getStage() == null) deal.setStage("Lead");
        return dealRepository.save(deal);
    }
    
    public List<ProjectDeal> getAllDeals() {
        return dealRepository.findAll();
    }
    
    public List<ProjectDeal> getDealsByStage(String stage) {
        return dealRepository.findByStage(stage);
    }
    
    public Optional<ProjectDeal> getDealById(Long id) {
        return dealRepository.findById(id);
    }
    
    @Transactional
    public ProjectDeal updateDeal(Long id, ProjectDeal deal) {
        ProjectDeal existing = dealRepository.findById(id).orElseThrow();
        existing.setName(deal.getName());
        existing.setClientId(deal.getClientId());
        existing.setValue(deal.getValue());
        existing.setScore(deal.getScore());
        existing.setStage(deal.getStage());
        existing.setUpdatedAt(new Date());
        return dealRepository.save(existing);
    }
    
    @Transactional
    public void deleteDeal(Long id) {
        dealRepository.deleteById(id);
    }

    // ============ AUTOMATIONS ============
    
    @Transactional
    public ProjectAutomation createAutomation(ProjectAutomation automation) {
        automation.setAutomationId("AUTO-" + String.format("%04d", System.currentTimeMillis() % 10000));
        automation.setCreatedAt(new Date());
        return automationRepository.save(automation);
    }
    
    public List<ProjectAutomation> getAllAutomations() {
        return automationRepository.findAll();
    }
    
    public List<ProjectAutomation> getAutomationsByTrigger(String trigger) {
        return automationRepository.findByTrigger(trigger);
    }
    
    public Optional<ProjectAutomation> getAutomationById(Long id) {
        return automationRepository.findById(id);
    }
    
    @Transactional
    public void deleteAutomation(Long id) {
        automationRepository.deleteById(id);
    }

    // ============ MESSAGES ============
    
    @Transactional
    public ProjectMessage createMessage(ProjectMessage message) {
        message.setMessageId("MSG-" + String.format("%04d", System.currentTimeMillis() % 10000));
        message.setCreatedAt(new Date());
        if (message.getTime() == null) message.setTime(new Date());
        return messageRepository.save(message);
    }
    
    public List<ProjectMessage> getMessagesByProject(Long projectId) {
        return messageRepository.findByProjectIdOrderByTimeAsc(projectId);
    }
    
    public Optional<ProjectMessage> getMessageById(Long id) {
        return messageRepository.findById(id);
    }
    
    @Transactional
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}