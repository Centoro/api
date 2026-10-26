package com.centoro.api.controller;

import com.centoro.api.dto.MonthlyOfferDTO;
import com.centoro.api.entities.MonthlyOffer;
import com.centoro.api.service.MonthlyOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Date;

@RestController
@RequestMapping("/api/monthly-offers")
@CrossOrigin(origins = "*")
public class MonthlyOfferController {

    @Autowired
    private MonthlyOfferService monthlyOfferService;

    @GetMapping
    public ResponseEntity<?> getAllOffers() {
        List<MonthlyOffer> offers = monthlyOfferService.getAllOffers();
        List<MonthlyOfferDTO> offerDTOs = offers.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", offerDTOs
        ));
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveOffers() {
        List<MonthlyOffer> offers = monthlyOfferService.getActiveOffers();
        List<MonthlyOfferDTO> offerDTOs = offers.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", offerDTOs
        ));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOffersByStatus(@PathVariable String status) {
        List<MonthlyOffer> offers = monthlyOfferService.getOffersByStatus(status);
        List<MonthlyOfferDTO> offerDTOs = offers.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", offerDTOs
        ));
    }

    @GetMapping("/month/{month}")
    public ResponseEntity<?> getOffersByMonth(@PathVariable String month) {
        List<MonthlyOffer> offers = monthlyOfferService.getOffersByStatus("live")
            .stream()
            .filter(o -> month.equalsIgnoreCase(o.getMonth()))
            .collect(Collectors.toList());
        List<MonthlyOfferDTO> offerDTOs = offers.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", offerDTOs
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOffer(@PathVariable Long id) {
        Optional<MonthlyOffer> offerOpt = monthlyOfferService.getOfferById(id);
        if (offerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(offerOpt.get())
        ));
    }

    @PostMapping
    public ResponseEntity<?> createOffer(@RequestBody MonthlyOffer offer) {
        MonthlyOffer saved = monthlyOfferService.createOffer(offer);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Monthly offer created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOffer(@PathVariable Long id, @RequestBody MonthlyOffer offer) {
        try {
            MonthlyOffer updated = monthlyOfferService.updateOffer(id, offer);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Monthly offer updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/claim")
    public ResponseEntity<?> claimOffer(@PathVariable Long id) {
        try {
            MonthlyOffer updated = monthlyOfferService.incrementClaims(id);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Offer claimed successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/download")
    public ResponseEntity<?> downloadOffer(@PathVariable Long id) {
        Optional<MonthlyOffer> offerOpt = monthlyOfferService.getOfferById(id);
        if (offerOpt.isEmpty()) return ResponseEntity.notFound().build();
        MonthlyOffer offer = offerOpt.get();
        if (!"live".equalsIgnoreCase(offer.getStatus()) || (offer.getExpiry() != null && offer.getExpiry().before(new Date()))) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "This Monthly Madness item is no longer available."));
        }
        if (offer.getSlots() != null && offer.getSlots() > 0 && (offer.getClaimed() == null ? 0 : offer.getClaimed()) >= offer.getSlots()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "This Monthly Madness item has reached its download limit."));
        }
        if (offer.getPrice() != null && offer.getPrice() > 0) {
            return ResponseEntity.ok(Map.of("success", true, "status", "payment_required", "price", offer.getPrice(), "item", offer.getTitle()));
        }
        MonthlyOffer updated = monthlyOfferService.incrementClaims(id);
        return ResponseEntity.ok(Map.of("success", true, "status", "free", "url", updated.getAudioUrl(), "data", convertToDTO(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable Long id) {
        if (!monthlyOfferService.getOfferById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        monthlyOfferService.deleteOffer(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Monthly offer deleted successfully"
        ));
    }

    private MonthlyOfferDTO convertToDTO(MonthlyOffer offer) {
        MonthlyOfferDTO dto = new MonthlyOfferDTO();
        dto.setId(offer.getId());
        dto.setOfferId(offer.getOfferId());
        dto.setTitle(offer.getTitle());
        dto.setType(offer.getType());
        dto.setGenre(offer.getGenre());
        dto.setMonth(offer.getMonth());
        dto.setDate(offer.getDate());
        dto.setExpiry(offer.getExpiry());
        dto.setBpm(offer.getBpm());
        dto.setDiscount(offer.getDiscount());
        dto.setPrice(offer.getPrice());
        dto.setSlots(offer.getSlots());
        dto.setClaimed(offer.getClaimed());
        dto.setCoverUrl(offer.getCoverUrl());
        dto.setAudioUrl(offer.getAudioUrl());
        dto.setStatus(offer.getStatus());
        dto.setCreatedAt(offer.getCreatedAt());
        dto.setUpdatedAt(offer.getUpdatedAt());
        return dto;
    }
}