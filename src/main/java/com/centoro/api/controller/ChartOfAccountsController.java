package com.centoro.api.controller;

import com.centoro.api.entities.ChartOfAccount;
import com.centoro.api.repositories.ChartOfAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounting/chart")
@CrossOrigin(origins = "*")
public class ChartOfAccountsController {

    @Autowired
    private ChartOfAccountRepository chartOfAccountRepository;

    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        List<ChartOfAccount> accounts = chartOfAccountRepository.findAll();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", accounts
        ));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getAccountsByType(@PathVariable String type) {
        List<ChartOfAccount> accounts = chartOfAccountRepository.findByType(type);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", accounts
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable Long id) {
        Optional<ChartOfAccount> accountOpt = chartOfAccountRepository.findById(id);
        if (accountOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", accountOpt.get()
        ));
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody ChartOfAccount account) {
        ChartOfAccount saved = chartOfAccountRepository.save(account);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Account created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody ChartOfAccount account) {
        Optional<ChartOfAccount> existingOpt = chartOfAccountRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ChartOfAccount existing = existingOpt.get();
        existing.setName(account.getName());
        existing.setType(account.getType());
        existing.setCode(account.getCode());
        existing.setDescription(account.getDescription());
        ChartOfAccount updated = chartOfAccountRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Account updated successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        if (!chartOfAccountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        chartOfAccountRepository.deleteById(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Account deleted successfully"
        ));
    }
}