package com.centoro.api.repositories;

import com.centoro.api.entities.StaffWarning;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StaffWarningRepository extends JpaRepository<StaffWarning, Long> {
    // ✅ FIX: Changed from Long to String
    List<StaffWarning> findByStaffId(String staffId);
}
