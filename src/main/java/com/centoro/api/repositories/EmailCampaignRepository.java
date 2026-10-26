package com.centoro.api.repositories;

import com.centoro.api.entities.EmailCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmailCampaignRepository extends JpaRepository<EmailCampaign, Long> {
    List<EmailCampaign> findByOrderBySentAtDesc();
    List<EmailCampaign> findByRecipient(String recipient);
}