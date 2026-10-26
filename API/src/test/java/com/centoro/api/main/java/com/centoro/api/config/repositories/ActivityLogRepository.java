package com.centoro.api.repositories;

import com.centoro.api.entities.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    
    Optional<ActivityLog> findByActivityId(String activityId);
    List<ActivityLog> findByUserId(String userId);  // ✅ FIXED - matches entity
    List<ActivityLog> findByOrderByCreatedAtDesc();
}