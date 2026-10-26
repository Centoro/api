package com.centoro.api.repositories;

import com.centoro.api.entities.MusicSample;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MusicSampleRepository extends JpaRepository<MusicSample, Long> {
    
    Optional<MusicSample> findBySampleId(String sampleId);
    List<MusicSample> findByCategory(String category);
    List<MusicSample> findByGenre(String genre);
    List<MusicSample> findByTitleContainingIgnoreCase(String title);
}