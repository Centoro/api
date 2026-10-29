package com.centoro.api.repositories;

import com.centoro.api.entities.ProjectMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMessageRepository extends JpaRepository<ProjectMessage, Long> {
    Optional<ProjectMessage> findByMessageId(String messageId);
    
    // FIX: projectId is String now (Project.id is UUID)
    List<ProjectMessage> findByProjectId(String projectId);
    
    // FIX: projectId is String now
    List<ProjectMessage> findByProjectIdOrderByTimeAsc(String projectId);
    
    // FIX: staffId is String now (Staff.id is UUID)
    List<ProjectMessage> findByStaffId(String staffId);
}
