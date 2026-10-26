package com.centoro.api.repositories;

import com.centoro.api.entities.CalendarNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CalendarNoteRepository extends JpaRepository<CalendarNote, Long> {
    Optional<CalendarNote> findByNoteId(String noteId);
    List<CalendarNote> findByDate(String date);
    List<CalendarNote> findByProjectId(Long projectId);
    
    @Query("SELECT n FROM CalendarNote n ORDER BY n.date ASC")
    List<CalendarNote> findUpcomingNotes();
    
    @Query("SELECT n FROM CalendarNote n WHERE n.date BETWEEN :start AND :end ORDER BY n.date ASC")
    List<CalendarNote> findNotesBetween(String start, String end);
}