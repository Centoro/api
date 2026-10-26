package com.centoro.api.repositories;

import com.centoro.api.entities.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    Optional<JournalEntry> findByJournalId(String journalId);
    List<JournalEntry> findBySourceId(String sourceId);
    List<JournalEntry> findByDateBetween(java.util.Date start, java.util.Date end);
}