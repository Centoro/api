package com.centoro.api.repositories;

import com.centoro.api.entities.FraudAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FraudAlertRepository extends JpaRepository<FraudAlert, Long> {
    List<FraudAlert> findByStatus(String status);
}