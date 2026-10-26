package com.centoro.api.repositories;

import com.centoro.api.entities.Integration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IntegrationRepository extends JpaRepository<Integration, Long> {
    List<Integration> findByStatus(String status);
}