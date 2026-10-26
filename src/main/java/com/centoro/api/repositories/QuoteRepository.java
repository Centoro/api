package com.centoro.api.repositories;

import com.centoro.api.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByClientId(Long clientId);
    List<Quote> findByStatus(String status);
}