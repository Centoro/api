package com.centoro.api.repositories;

import com.centoro.api.entities.WebsiteSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WebsiteSlideRepository extends JpaRepository<WebsiteSlide, Long> {
    
    Optional<WebsiteSlide> findBySlideId(String slideId);
    List<WebsiteSlide> findByActiveTrue();
}