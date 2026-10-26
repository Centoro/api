package com.centoro.api.repositories;

import com.centoro.api.entities.ProjectMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProjectMessageRepository extends JpaRepository<ProjectMessage, Long> {
    Optional<ProjectMessage> findByMessageId(String messageId);
    List<ProjectMessage> findByProjectId(Long projectId);
    List<ProjectMessage> findByProjectIdOrderByTimeAsc(Long projectId);
    List<ProjectMessage> findByStaffId(Long staffId);
}