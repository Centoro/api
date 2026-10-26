package com.centoro.api.controller;

import com.centoro.api.dto.CalendarNoteDTO;
import com.centoro.api.entities.CalendarNote;
import com.centoro.api.service.CalendarNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calendar/notes")
@CrossOrigin(origins = "*")
public class CalendarNoteController {

    @Autowired
    private CalendarNoteService calendarNoteService;

    @GetMapping
    public ResponseEntity<?> getAllNotes() {
        List<CalendarNote> notes = calendarNoteService.getAllNotes();
        List<CalendarNoteDTO> noteDTOs = notes.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", noteDTOs
        ));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingNotes() {
        List<CalendarNote> notes = calendarNoteService.getUpcomingNotes();
        List<CalendarNoteDTO> noteDTOs = notes.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", noteDTOs
        ));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> getNotesByDate(@PathVariable String date) {
        List<CalendarNote> notes = calendarNoteService.getNotesByDate(date);
        List<CalendarNoteDTO> noteDTOs = notes.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", noteDTOs
        ));
    }

    @GetMapping("/range")
    public ResponseEntity<?> getNotesBetween(
            @RequestParam String start,
            @RequestParam String end) {
        List<CalendarNote> notes = calendarNoteService.getNotesBetween(start, end);
        List<CalendarNoteDTO> noteDTOs = notes.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", noteDTOs
        ));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getNotesByProject(@PathVariable Long projectId) {
        List<CalendarNote> notes = calendarNoteService.getNotesByProject(projectId);
        List<CalendarNoteDTO> noteDTOs = notes.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", noteDTOs
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(@PathVariable Long id) {
        Optional<CalendarNote> noteOpt = calendarNoteService.getNoteById(id);
        if (noteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(noteOpt.get())
        ));
    }

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody CalendarNote note) {
        CalendarNote saved = calendarNoteService.createNote(note);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Calendar note created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody CalendarNote note) {
        try {
            CalendarNote updated = calendarNoteService.updateNote(id, note);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Calendar note updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        if (!calendarNoteService.getNoteById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        calendarNoteService.deleteNote(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Calendar note deleted successfully"
        ));
    }

    private CalendarNoteDTO convertToDTO(CalendarNote note) {
        CalendarNoteDTO dto = new CalendarNoteDTO();
        dto.setId(note.getId());
        dto.setNoteId(note.getNoteId());
        dto.setDate(note.getDate());
        dto.setProjectId(note.getProjectId());
        dto.setText(note.getText());
        dto.setImportance(note.getImportance());
        dto.setCreatedBy(note.getCreatedBy());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setUpdatedAt(note.getUpdatedAt());
        return dto;
    }
}