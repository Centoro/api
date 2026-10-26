package com.centoro.api.controller;

import com.centoro.api.dto.BankTransactionDTO;
import com.centoro.api.entities.BankTransaction;
import com.centoro.api.service.BankTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bank-transactions")
@CrossOrigin(origins = "*")
public class BankTransactionController {

    @Autowired
    private BankTransactionService bankTransactionService;

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        List<BankTransaction> transactions = bankTransactionService.getAllTransactions();
        List<BankTransactionDTO> transactionDTOs = transactions.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", transactionDTOs
        ));
    }

    @GetMapping("/unreconciled")
    public ResponseEntity<?> getUnreconciledTransactions() {
        List<BankTransaction> transactions = bankTransactionService.getUnreconciledTransactions();
        List<BankTransactionDTO> transactionDTOs = transactions.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", transactionDTOs
        ));
    }

    @GetMapping("/date-range")
    public ResponseEntity<?> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        List<BankTransaction> transactions = bankTransactionService.getTransactionsByDateRange(start, end);
        List<BankTransactionDTO> transactionDTOs = transactions.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", transactionDTOs
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable Long id) {
        Optional<BankTransaction> transactionOpt = bankTransactionService.getTransactionById(id);
        if (transactionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(transactionOpt.get())
        ));
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody BankTransaction transaction) {
        BankTransaction saved = bankTransactionService.createTransaction(transaction);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Bank transaction created successfully"
        ));
    }

    @PostMapping("/{id}/reconcile")
    public ResponseEntity<?> reconcileTransaction(@PathVariable Long id) {
        try {
            BankTransaction reconciled = bankTransactionService.reconcileTransaction(id);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(reconciled),
                "message", "Transaction reconciled successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        if (!bankTransactionService.getTransactionById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        bankTransactionService.deleteTransaction(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Bank transaction deleted successfully"
        ));
    }

    private BankTransactionDTO convertToDTO(BankTransaction transaction) {
        BankTransactionDTO dto = new BankTransactionDTO();
        dto.setId(transaction.getId());
        dto.setBankId(transaction.getBankId());
        dto.setDate(transaction.getDate());
        dto.setDescription(transaction.getDescription());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setReference(transaction.getReference());
        dto.setReconciled(transaction.getReconciled());
        dto.setCreatedAt(transaction.getCreatedAt());
        return dto;
    }
}