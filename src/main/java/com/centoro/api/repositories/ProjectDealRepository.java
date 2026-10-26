package com.centoro.api.repositories;

import com.centoro.api.entities.ProjectDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProjectDealRepository extends JpaRepository<ProjectDeal, Long> {
    Optional<ProjectDeal> findByDealId(String dealId);
    List<ProjectDeal> findByStage(String stage);
    List<ProjectDeal> findByClientId(Long clientId);
    List<ProjectDeal> findByStageOrderByValueDesc(String stage);
}