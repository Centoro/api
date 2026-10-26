package com.centoro.api.repositories;

import com.centoro.api.entities.PortRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortRuleRepository extends JpaRepository<PortRule, Long> {
}