package com.centoro.api.repositories;

import com.centoro.api.entities.TaxPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TaxPeriodRepository extends JpaRepository<TaxPeriod, Long> {
    
    Optional<TaxPeriod> findByPeriod(String period);
}