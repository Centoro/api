package com.centoro.api.repositories;

import com.centoro.api.entities.CompanySettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanySettingsRepository extends JpaRepository<CompanySettings, Long> {
}