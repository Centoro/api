package com.centoro.api.repositories;

import com.centoro.api.entities.WebsiteNews;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WebsiteNewsRepository extends JpaRepository<WebsiteNews, Long> {
    
    Optional<WebsiteNews> findByNewsId(String newsId);
    List<WebsiteNews> findByPublishedTrue();
    List<WebsiteNews> findByCategory(String category);
    List<WebsiteNews> findByPublishedTrueOrderByDateDesc();
}