package com.centoro.api.controller;

import com.centoro.api.entities.CompetitionEntry;
import com.centoro.api.repositories.CompetitionEntryRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/competitions")
@CrossOrigin(origins = "*")
public class CompetitionController {
    
    private final CompetitionEntryRepository competitionRepository;
    
    public CompetitionController(CompetitionEntryRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }
    
    @GetMapping 
    public List<CompetitionEntry> getAllEntries() { 
        return competitionRepository.findAll(); 
    }
    
    @GetMapping("/user/{userId}") 
    public List<CompetitionEntry> getEntriesByUser(@PathVariable Long userId) { 
        return competitionRepository.findByUserId(userId); 
    }
    
    @PostMapping 
    public CompetitionEntry createEntry(@RequestBody CompetitionEntry entry) { 
        return competitionRepository.save(entry); 
    }
}