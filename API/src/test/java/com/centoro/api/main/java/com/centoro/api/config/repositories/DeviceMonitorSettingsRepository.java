package com.centoro.api.repositories;

import com.centoro.api.entities.DeviceMonitorSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceMonitorSettingsRepository extends JpaRepository<DeviceMonitorSettings, Long> {
}