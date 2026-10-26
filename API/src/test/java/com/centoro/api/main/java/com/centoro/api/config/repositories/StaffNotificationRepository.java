package com.centoro.api.repositories;

import com.centoro.api.entities.StaffNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface StaffNotificationRepository extends JpaRepository<StaffNotification, Long> {
    Optional<StaffNotification> findByNotificationId(String notificationId);
    List<StaffNotification> findByTarget(String target);
    List<StaffNotification> findByOrderByTimeDesc();
    List<StaffNotification> findByDepartment(String department);
    
    @Query("SELECT n FROM StaffNotification n WHERE n.target = 'all' OR n.target = 'all-depts' ORDER BY n.time DESC")
    List<StaffNotification> findGlobalNotifications();
}