package com.centoro.api.repositories;

import com.centoro.api.entities.ProjectDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectDealRepository extends JpaRepository<ProjectDeal, Long> {
    List<ProjectDeal> findByStage(String stage);
    List<ProjectDeal> findByClientId(String clientId);
}
