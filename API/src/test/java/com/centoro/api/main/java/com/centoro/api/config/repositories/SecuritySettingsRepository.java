package com.centoro.api.repositories;

import com.centoro.api.entities.SecuritySettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecuritySettingsRepository extends JpaRepository<SecuritySettings, Long> {
}