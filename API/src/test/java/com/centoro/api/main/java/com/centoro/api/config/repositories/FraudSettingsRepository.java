package com.centoro.api.repositories;

import com.centoro.api.entities.FraudSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudSettingsRepository extends JpaRepository<FraudSettings, Long> {
}