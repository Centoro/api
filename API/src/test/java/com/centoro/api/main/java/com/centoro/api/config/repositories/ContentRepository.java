package com.centoro.api.repositories;

import com.centoro.api.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByType(String type);
    List<Content> findByIsPublishedTrue();
    List<Content> findByIsFeaturedTrue();
    List<Content> findByGenre(String genre);
    List<Content> findByArtist(String artist);
}