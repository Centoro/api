package com.centoro.api.repositories;

import com.centoro.api.entities.MusicTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MusicTrackRepository extends JpaRepository<MusicTrack, Long> {
    
    Optional<MusicTrack> findByTrackId(String trackId);
    List<MusicTrack> findByGenre(String genre);
    List<MusicTrack> findByGenreContainingIgnoreCase(String genre);
    List<MusicTrack> findByTitleContainingIgnoreCase(String title);
}