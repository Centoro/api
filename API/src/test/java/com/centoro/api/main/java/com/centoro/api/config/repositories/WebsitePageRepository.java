package com.centoro.api.repositories;

import com.centoro.api.entities.WebsitePage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WebsitePageRepository extends JpaRepository<WebsitePage, Long> {
    List<WebsitePage> findByIsPublishedTrue();
    List<WebsitePage> findByIsMainTrue();
    WebsitePage findBySlug(String slug);
}