package com.centoro.api.repositories;

import com.centoro.api.entities.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<PortfolioItem, Long> {
    Optional<PortfolioItem> findByPortfolioId(String portfolioId);
    List<PortfolioItem> findByCategory(String category);
    List<PortfolioItem> findByStatus(String status);
    List<PortfolioItem> findByCategoryAndStatus(String category, String status);
}