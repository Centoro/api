package com.centoro.api.repositories;

import com.centoro.api.entities.AiProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AiProviderRepository extends JpaRepository<AiProvider, Long> {
    List<AiProvider> findByStatus(String status);
    List<AiProvider> findByIsOwnTrue();
}