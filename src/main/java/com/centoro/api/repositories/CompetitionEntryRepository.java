package com.centoro.api.repositories;
import com.centoro.api.entities.CompetitionEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CompetitionEntryRepository extends JpaRepository<CompetitionEntry, Long> {
    List<CompetitionEntry> findByUserId(Long userId);
    List<CompetitionEntry> findByCategory(String category);
}