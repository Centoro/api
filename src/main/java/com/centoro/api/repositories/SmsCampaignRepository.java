package com.centoro.api.repositories;

import com.centoro.api.entities.SmsCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SmsCampaignRepository extends JpaRepository<SmsCampaign, Long> {
    Optional<SmsCampaign> findByCampaignId(String campaignId);
    List<SmsCampaign> findByOrderBySentAtDesc();
}