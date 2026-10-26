package com.centoro.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.centoro.api.entities.Quote;
import com.centoro.api.repositories.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@RestController
@RequestMapping("/api/quotes")
@CrossOrigin(origins = "*")
public class QuotesController {
    
    @Autowired
    private QuoteRepository quoteRepository;
    
    @GetMapping
    public ResponseEntity<?> getAllQuotes() {
        return ResponseEntity.ok(Map.of("success", true, "data", quoteRepository.findAll()));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getQuotesByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(Map.of("success", true, "data", quoteRepository.findByClientId(clientId)));
    }
    
    @PostMapping
    public ResponseEntity<?> createQuote(@RequestBody Quote quote) {
        quote.setQuoteId("QTE-" + System.currentTimeMillis());
        quote.setDate(new Date());
        quote.setCreatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", quoteRepository.save(quote)));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuote(@PathVariable Long id, @RequestBody Quote quote) {
        return quoteRepository.findById(id)
            .map(existing -> {
                existing.setDescription(quote.getDescription());
                existing.setAmount(quote.getAmount());
                existing.setDiscountPct(quote.getDiscountPct());
                existing.setTotal(quote.getTotal());
                existing.setValidUntil(quote.getValidUntil());
                existing.setStatus(quote.getStatus());
                existing.setUpdatedAt(new Date());
                return ResponseEntity.ok(Map.of("success", true, "data", quoteRepository.save(existing)));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuote(@PathVariable Long id) {
        if (quoteRepository.existsById(id)) {
            quoteRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("success", true));
        }
        return ResponseEntity.notFound().build();
    }
}