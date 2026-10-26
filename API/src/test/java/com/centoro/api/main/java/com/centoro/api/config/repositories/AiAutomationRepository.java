package com.centoro.api.repositories;

import com.centoro.api.entities.AiAutomation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AiAutomationRepository extends JpaRepository<AiAutomation, Long> {
    List<AiAutomation> findByEnabledTrue();
}