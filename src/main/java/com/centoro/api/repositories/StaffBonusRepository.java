package com.centoro.api.repositories;

import com.centoro.api.entities.StaffBonus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StaffBonusRepository extends JpaRepository<StaffBonus, Long> {
    // ✅ FIX: This MUST be String to match the Controller and Database
    List<StaffBonus> findByStaffId(String staffId);
}
