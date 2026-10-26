package com.centoro.api.repositories;

import com.centoro.api.entities.StaffWarning;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StaffWarningRepository extends JpaRepository<StaffWarning, Long> {
    List<StaffWarning> findByStaffId(Long staffId);
}