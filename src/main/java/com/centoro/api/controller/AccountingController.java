package com.centoro.api.controller;

import com.centoro.api.dto.JournalEntryDTO;
import com.centoro.api.dto.JournalLineDTO;
import com.centoro.api.entities.JournalEntry;
import com.centoro.api.entities.JournalLine;
import com.centoro.api.service.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounting")
@CrossOrigin(origins = "*")
public class AccountingController {

    @Autowired
    private AccountingService accountingService;

    // ============ JOURNAL ENTRIES ============
    
    @GetMapping("/journal")
    public ResponseEntity<?> getAllJournalEntries() {
        List<JournalEntry> entries = accountingService.getAllJournalEntries();
        List<JournalEntryDTO> entryDTOs = entries.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", entryDTOs
        ));
    }

    @GetMapping("/journal/{id}")
    public ResponseEntity<?> getJournalEntry(@PathVariable Long id) {
        Optional<JournalEntry> entryOpt = accountingService.getJournalEntryById(id);
        if (entryOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        JournalEntryDTO dto = convertToDTO(entryOpt.get());
        dto.setLines(accountingService.getJournalLines(id).stream()
            .map(this::convertLineToDTO)
            .collect(Collectors.toList()));
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", dto
        ));
    }

    @PostMapping("/journal")
    public ResponseEntity<?> createJournalEntry(@RequestBody JournalEntry entry) {
        JournalEntry saved = accountingService.createJournalEntry(entry);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Journal entry created successfully"
        ));
    }

    @PutMapping("/journal/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable Long id, @RequestBody JournalEntry entry) {
        try {
            JournalEntry updated = accountingService.updateJournalEntry(id, entry);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Journal entry updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/journal/{id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable Long id) {
        if (!accountingService.getJournalEntryById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        accountingService.deleteJournalEntry(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Journal entry deleted successfully"
        ));
    }

    @GetMapping("/journal/trial-balance")
    public ResponseEntity<?> getTrialBalance() {
        Map<String, Double> balances = accountingService.getTrialBalance();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", balances
        ));
    }

    // ============ BUDGETS ============
    
    @GetMapping("/budgets")
    public ResponseEntity<?> getAllBudgets() {
        // This is handled by BudgetController at /api/budgets
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Use /api/budgets endpoint for budget operations"
        ));
    }

    // ============ CONVERTERS ============

    private JournalEntryDTO convertToDTO(JournalEntry entry) {
        JournalEntryDTO dto = new JournalEntryDTO();
        dto.setId(entry.getId());
        dto.setJournalId(entry.getJournalId());
        dto.setDate(entry.getDate());
        dto.setDescription(entry.getDescription());
        dto.setReference(entry.getReference());
        dto.setSourceId(entry.getSourceId());
        dto.setCreatedAt(entry.getCreatedAt());
        return dto;
    }

    private JournalLineDTO convertLineToDTO(JournalLine line) {
        JournalLineDTO dto = new JournalLineDTO();
        dto.setId(line.getId());
        dto.setJournalEntryId(line.getJournalEntryId());
        dto.setAccount(line.getAccount());
        dto.setDebit(line.getDebit());
        dto.setCredit(line.getCredit());
        return dto;
    }
}