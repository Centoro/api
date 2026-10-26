package com.centoro.api.repositories;

import com.centoro.api.entities.Infrastructure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfrastructureRepository extends JpaRepository<Infrastructure, Long> {
}