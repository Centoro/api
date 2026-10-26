package com.centoro.api.repositories;

import com.centoro.api.entities.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {
    Optional<Analytics> findByAnalyticsId(String analyticsId);
    List<Analytics> findByType(String type);
    List<Analytics> findByTypeOrderByCountDesc(String type);
    List<Analytics> findByCountryOrderByCountDesc(String country);
    
    @Query("SELECT a FROM Analytics a WHERE a.type = 'country' ORDER BY a.count DESC")
    List<Analytics> findTopCountries();
    
    @Query("SELECT a FROM Analytics a WHERE a.type = 'interaction' ORDER BY a.time DESC")
    List<Analytics> findRecentInteractions();
}