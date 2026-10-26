package com.centoro.api.repositories;

import com.centoro.api.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    
    Optional<Currency> findByCode(String code);
    Optional<Currency> findByIsBaseTrue();
}