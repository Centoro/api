package com.centoro.api.repositories;

import com.centoro.api.entities.JobCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JobCardRepository extends JpaRepository<JobCard, Long> {
    
    Optional<JobCard> findByJobCardId(String jobCardId);
    List<JobCard> findByClientId(Long clientId);
    List<JobCard> findByProjectId(Long projectId);
    List<JobCard> findByAssigneeId(Long assigneeId);
    List<JobCard> findByStatus(String status);
    List<JobCard> findByPriority(String priority);
}