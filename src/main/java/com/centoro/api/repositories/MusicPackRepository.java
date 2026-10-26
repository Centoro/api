package com.centoro.api.repositories;

import com.centoro.api.entities.MusicPack;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MusicPackRepository extends JpaRepository<MusicPack, Long> {
    
    Optional<MusicPack> findByPackId(String packId);
    List<MusicPack> findByGenre(String genre);
    List<MusicPack> findByTitleContainingIgnoreCase(String title);
}