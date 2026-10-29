package com.centoro.api.repositories;

import com.centoro.api.entities.ProjectAutomation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectAutomationRepository extends JpaRepository<ProjectAutomation, Long> {
    List<ProjectAutomation> findByTrigger(String trigger);
}
