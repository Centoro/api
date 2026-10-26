package com.centoro.api.repositories;

import com.centoro.api.entities.BarcodeIntegration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarcodeIntegrationRepository extends JpaRepository<BarcodeIntegration, Long> {
}