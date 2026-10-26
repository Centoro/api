package com.centoro.api.service;

import com.centoro.api.entities.CalendarNote;
import com.centoro.api.repositories.CalendarNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarNoteService {
    
    @Autowired
    private CalendarNoteRepository calendarNoteRepository;
    
    @Transactional
    public CalendarNote createNote(CalendarNote note) {
        note.setNoteId("CND-" + String.format("%04d", System.currentTimeMillis() % 10000));
        note.setCreatedAt(new Date());
        note.setUpdatedAt(new Date());
        if (note.getImportance() == null) note.setImportance("medium");
        return calendarNoteRepository.save(note);
    }
    
    public List<CalendarNote> getAllNotes() {
        return calendarNoteRepository.findAll();
    }
    
    public List<CalendarNote> getUpcomingNotes() {
        return calendarNoteRepository.findUpcomingNotes();
    }
    
    public List<CalendarNote> getNotesByDate(String date) {
        return calendarNoteRepository.findByDate(date);
    }
    
    public List<CalendarNote> getNotesBetween(String start, String end) {
        return calendarNoteRepository.findNotesBetween(start, end);
    }
    
    public List<CalendarNote> getNotesByProject(Long projectId) {
        return calendarNoteRepository.findByProjectId(projectId);
    }
    
    public Optional<CalendarNote> getNoteById(Long id) {
        return calendarNoteRepository.findById(id);
    }
    
    @Transactional
    public CalendarNote updateNote(Long id, CalendarNote note) {
        CalendarNote existing = calendarNoteRepository.findById(id).orElseThrow();
        existing.setDate(note.getDate());
        existing.setProjectId(note.getProjectId());
        existing.setText(note.getText());
        existing.setImportance(note.getImportance());
        existing.setUpdatedAt(new Date());
        return calendarNoteRepository.save(existing);
    }
    
    @Transactional
    public void deleteNote(Long id) {
        calendarNoteRepository.deleteById(id);
    }
}