package com.centoro.api.repositories;
import com.centoro.api.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SongRepository extends JpaRepository<Song, Long> {}