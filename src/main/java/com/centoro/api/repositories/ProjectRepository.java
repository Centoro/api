package com.centoro.api.repositories;

import com.centoro.api.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    Optional<Project> findByProjectId(String projectId);
    List<Project> findByClientId(Long clientId);
    List<Project> findByStatus(Project.ProjectStatus status);
    List<Project> findByClientIdAndStatus(Long clientId, Project.ProjectStatus status);
    List<Project> findByServiceContainingIgnoreCase(String service);
    
    @Query("SELECT p FROM Project p WHERE p.clientId = :clientId AND p.deletedAt IS NULL")
    List<Project> findActiveByClientId(@Param("clientId") Long clientId);
    
    @Query("SELECT p FROM Project p WHERE p.status = :status AND p.dueDate < CURRENT_DATE")
    List<Project> findOverdueByStatus(@Param("status") Project.ProjectStatus status);
    
    boolean existsByProjectId(String projectId);
}