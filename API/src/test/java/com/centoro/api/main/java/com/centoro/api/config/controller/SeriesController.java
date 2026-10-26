package com.centoro.api.controller;

import com.centoro.api.entities.FridayDrop;
import com.centoro.api.entities.MonthlyOffer;
import com.centoro.api.repositories.FridayDropRepository;
import com.centoro.api.repositories.MonthlyOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/series")
@CrossOrigin(origins = "*")
public class SeriesController {

    @Autowired
    private FridayDropRepository fridayDropRepository;
    
    @Autowired
    private MonthlyOfferRepository monthlyOfferRepository;

    // ============ FRIDAY DROPS ============
    
    @GetMapping("/friday")
    public ResponseEntity<?> getAllFridayDrops() {
        List<FridayDrop> drops = fridayDropRepository.findAll();
        drops.removeIf(d -> d.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", drops
        ));
    }

    @GetMapping("/friday/active")
    public ResponseEntity<?> getActiveFridayDrops() {
        List<FridayDrop> drops = fridayDropRepository.findActiveDrops();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", drops
        ));
    }

    @GetMapping("/friday/{id}")
    public ResponseEntity<?> getFridayDrop(@PathVariable Long id) {
        Optional<FridayDrop> drop = fridayDropRepository.findById(id);
        if (drop.isEmpty() || drop.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", drop.get()
        ));
    }

    @PostMapping("/friday")
    public ResponseEntity<?> createFridayDrop(@RequestBody FridayDrop drop) {
        drop.setDropId("DRP-" + String.format("%04d", System.currentTimeMillis() % 10000));
        drop.setCreatedAt(new Date());
        drop.setUpdatedAt(new Date());
        drop.setStatus("live");
        
        if (drop.getClaimed() == null) drop.setClaimed(0);
        if (drop.getType() == null) drop.setType("audio");
        
        FridayDrop saved = fridayDropRepository.save(drop);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Friday drop created successfully"
        ));
    }

    @PutMapping("/friday/{id}")
    public ResponseEntity<?> updateFridayDrop(@PathVariable Long id, @RequestBody FridayDrop drop) {
        Optional<FridayDrop> existingOpt = fridayDropRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        FridayDrop existing = existingOpt.get();
        existing.setTitle(drop.getTitle());
        existing.setType(drop.getType());
        existing.setGenre(drop.getGenre());
        existing.setDate(drop.getDate());
        existing.setExpiry(drop.getExpiry());
        existing.setBpm(drop.getBpm());
        existing.setDiscount(drop.getDiscount());
        existing.setSlots(drop.getSlots());
        existing.setClaimed(drop.getClaimed());
        existing.setCoverUrl(drop.getCoverUrl());
        existing.setAudioUrl(drop.getAudioUrl());
        existing.setStatus(drop.getStatus());
        existing.setUpdatedAt(new Date());
        
        FridayDrop updated = fridayDropRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Friday drop updated successfully"
        ));
    }

    @DeleteMapping("/friday/{id}")
    public ResponseEntity<?> deleteFridayDrop(@PathVariable Long id) {
        Optional<FridayDrop> dropOpt = fridayDropRepository.findById(id);
        if (dropOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        FridayDrop drop = dropOpt.get();
        drop.setDeletedAt(new Date());
        fridayDropRepository.save(drop);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Friday drop deleted successfully"
        ));
    }

    // ============ MONTHLY OFFERS ============
    
    @GetMapping("/monthly")
    public ResponseEntity<?> getAllMonthlyOffers() {
        List<MonthlyOffer> offers = monthlyOfferRepository.findAll();
        offers.removeIf(o -> o.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", offers
        ));
    }

    @GetMapping("/monthly/active")
    public ResponseEntity<?> getActiveMonthlyOffers() {
        List<MonthlyOffer> offers = monthlyOfferRepository.findActiveOffers();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", offers
        ));
    }

    @GetMapping("/monthly/{id}")
    public ResponseEntity<?> getMonthlyOffer(@PathVariable Long id) {
        Optional<MonthlyOffer> offer = monthlyOfferRepository.findById(id);
        if (offer.isEmpty() || offer.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", offer.get()
        ));
    }

    @PostMapping("/monthly")
    public ResponseEntity<?> createMonthlyOffer(@RequestBody MonthlyOffer offer) {
        offer.setOfferId("MOF-" + String.format("%04d", System.currentTimeMillis() % 10000));
        offer.setCreatedAt(new Date());
        offer.setUpdatedAt(new Date());
        offer.setStatus("live");
        
        if (offer.getClaimed() == null) offer.setClaimed(0);
        if (offer.getType() == null) offer.setType("service");
        
        MonthlyOffer saved = monthlyOfferRepository.save(offer);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Monthly offer created successfully"
        ));
    }

    @PutMapping("/monthly/{id}")
    public ResponseEntity<?> updateMonthlyOffer(@PathVariable Long id, @RequestBody MonthlyOffer offer) {
        Optional<MonthlyOffer> existingOpt = monthlyOfferRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        MonthlyOffer existing = existingOpt.get();
        existing.setTitle(offer.getTitle());
        existing.setType(offer.getType());
        existing.setGenre(offer.getGenre());
        existing.setMonth(offer.getMonth());
        existing.setDate(offer.getDate());
        existing.setExpiry(offer.getExpiry());
        existing.setBpm(offer.getBpm());
        existing.setDiscount(offer.getDiscount());
        existing.setSlots(offer.getSlots());
        existing.setClaimed(offer.getClaimed());
        existing.setCoverUrl(offer.getCoverUrl());
        existing.setAudioUrl(offer.getAudioUrl());
        existing.setStatus(offer.getStatus());
        existing.setUpdatedAt(new Date());
        
        MonthlyOffer updated = monthlyOfferRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Monthly offer updated successfully"
        ));
    }

    @DeleteMapping("/monthly/{id}")
    public ResponseEntity<?> deleteMonthlyOffer(@PathVariable Long id) {
        Optional<MonthlyOffer> offerOpt = monthlyOfferRepository.findById(id);
        if (offerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        MonthlyOffer offer = offerOpt.get();
        offer.setDeletedAt(new Date());
        monthlyOfferRepository.save(offer);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Monthly offer deleted successfully"
        ));
    }
}

