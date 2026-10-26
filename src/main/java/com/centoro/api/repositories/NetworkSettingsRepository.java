package com.centoro.api.repositories;

import com.centoro.api.entities.NetworkSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkSettingsRepository extends JpaRepository<NetworkSettings, Long> {
}