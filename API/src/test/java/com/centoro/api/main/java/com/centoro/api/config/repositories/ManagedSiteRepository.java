package com.centoro.api.repositories;

import com.centoro.api.entities.ManagedSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagedSiteRepository extends JpaRepository<ManagedSite, Long> {
}