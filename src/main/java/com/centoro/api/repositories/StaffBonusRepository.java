package com.centoro.api.repositories;

import com.centoro.api.entities.StaffBonus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StaffBonusRepository extends JpaRepository<StaffBonus, Long> {
    List<StaffBonus> findByStaffId(Long staffId);
    List<StaffBonus> findByStatus(String status);
}