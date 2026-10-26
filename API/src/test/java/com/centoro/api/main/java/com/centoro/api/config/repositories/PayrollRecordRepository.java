package com.centoro.api.repositories;

import com.centoro.api.entities.PayrollRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PayrollRecordRepository extends JpaRepository<PayrollRecord, Long> {
    Optional<PayrollRecord> findByRecordId(String recordId);
    List<PayrollRecord> findByStaffId(Long staffId);
    List<PayrollRecord> findByPeriod(String period);
    List<PayrollRecord> findByPeriodAndStatus(String period, String status);
    List<PayrollRecord> findByPeriodOrderByStaffIdAsc(String period);
    
    @Query("SELECT p FROM PayrollRecord p WHERE p.period = :period AND p.status = 'pending'")
    List<PayrollRecord> findPendingByPeriod(String period);
}