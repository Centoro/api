package com.centoro.api.repositories;

import com.centoro.api.entities.ProjectAutomation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProjectAutomationRepository extends JpaRepository<ProjectAutomation, Long> {
    Optional<ProjectAutomation> findByAutomationId(String automationId);
    List<ProjectAutomation> findByTrigger(String trigger);
}