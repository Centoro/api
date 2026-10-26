package com.centoro.api.repositories;

import com.centoro.api.entities.PlatformKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformKeyRepository extends JpaRepository<PlatformKey, Long> {
}