package com.centoro.api.repositories;

import com.centoro.api.entities.SmsProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SmsProviderRepository extends JpaRepository<SmsProvider, Long> {
    Optional<SmsProvider> findByProviderId(String providerId);
    List<SmsProvider> findByStatus(String status);
    Optional<SmsProvider> findByProvider(String provider);
}