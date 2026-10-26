package com.centoro.api.repositories;

import com.centoro.api.entities.JournalLine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JournalLineRepository extends JpaRepository<JournalLine, Long> {
    List<JournalLine> findByJournalEntryId(Long journalEntryId);
}