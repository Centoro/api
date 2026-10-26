package com.centoro.api.repositories;

import com.centoro.api.entities.WebsiteStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WebsiteStatsRepository extends JpaRepository<WebsiteStats, Long> {
    Optional<WebsiteStats> findByStatsId(String statsId);
    List<WebsiteStats> findByDateBetween(Date start, Date end);
    Optional<WebsiteStats> findTopByOrderByDateDesc();
    
    @Query("SELECT SUM(w.visitors) FROM WebsiteStats w")
    Long getTotalVisitors();
    
    @Query("SELECT SUM(w.downloads) FROM WebsiteStats w")
    Long getTotalDownloads();
}