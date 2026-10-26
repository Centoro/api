package com.centoro.api.controller;

import com.centoro.api.dto.FridayDropDTO;
import com.centoro.api.entities.FridayDrop;
import com.centoro.api.service.FridayDropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Date;

@RestController
@RequestMapping("/api/friday-drops")
@CrossOrigin(origins = "*")
public class FridayDropController {

    @Autowired
    private FridayDropService fridayDropService;

    @GetMapping
    public ResponseEntity<?> getAllDrops() {
        List<FridayDrop> drops = fridayDropService.getAllDrops();
        List<FridayDropDTO> dropDTOs = drops.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", dropDTOs
        ));
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveDrops() {
        List<FridayDrop> drops = fridayDropService.getActiveDrops();
        List<FridayDropDTO> dropDTOs = drops.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", dropDTOs
        ));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getDropsByStatus(@PathVariable String status) {
        List<FridayDrop> drops = fridayDropService.getDropsByStatus(status);
        List<FridayDropDTO> dropDTOs = drops.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", dropDTOs
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDrop(@PathVariable Long id) {
        Optional<FridayDrop> dropOpt = fridayDropService.getDropById(id);
        if (dropOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(dropOpt.get())
        ));
    }

    @PostMapping
    public ResponseEntity<?> createDrop(@RequestBody FridayDrop drop) {
        FridayDrop saved = fridayDropService.createDrop(drop);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Friday drop created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDrop(@PathVariable Long id, @RequestBody FridayDrop drop) {
        try {
            FridayDrop updated = fridayDropService.updateDrop(id, drop);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Friday drop updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/claim")
    public ResponseEntity<?> claimDrop(@PathVariable Long id) {
        try {
            FridayDrop updated = fridayDropService.incrementClaims(id);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Drop claimed successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/download")
    public ResponseEntity<?> downloadDrop(@PathVariable Long id) {
        Optional<FridayDrop> dropOpt = fridayDropService.getDropById(id);
        if (dropOpt.isEmpty()) return ResponseEntity.notFound().build();
        FridayDrop drop = dropOpt.get();
        if (!"live".equalsIgnoreCase(drop.getStatus()) || (drop.getExpiry() != null && drop.getExpiry().before(new Date()))) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "This Friday Madness item is no longer available."));
        }
        if (drop.getSlots() != null && drop.getSlots() > 0 && (drop.getClaimed() == null ? 0 : drop.getClaimed()) >= drop.getSlots()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "This Friday Madness item has reached its download limit."));
        }
        if (drop.getPrice() != null && drop.getPrice() > 0) {
            return ResponseEntity.ok(Map.of("success", true, "status", "payment_required", "price", drop.getPrice(), "item", drop.getTitle()));
        }
        FridayDrop updated = fridayDropService.incrementClaims(id);
        return ResponseEntity.ok(Map.of("success", true, "status", "free", "url", updated.getAudioUrl(), "data", convertToDTO(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDrop(@PathVariable Long id) {
        if (!fridayDropService.getDropById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        fridayDropService.deleteDrop(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Friday drop deleted successfully"
        ));
    }

    private FridayDropDTO convertToDTO(FridayDrop drop) {
        FridayDropDTO dto = new FridayDropDTO();
        dto.setId(drop.getId());
        dto.setDropId(drop.getDropId());
        dto.setTitle(drop.getTitle());
        dto.setType(drop.getType());
        dto.setGenre(drop.getGenre());
        dto.setDate(drop.getDate());
        dto.setExpiry(drop.getExpiry());
        dto.setBpm(drop.getBpm());
        dto.setDiscount(drop.getDiscount());
        dto.setPrice(drop.getPrice());
        dto.setSlots(drop.getSlots());
        dto.setClaimed(drop.getClaimed());
        dto.setCoverUrl(drop.getCoverUrl());
        dto.setAudioUrl(drop.getAudioUrl());
        dto.setStatus(drop.getStatus());
        dto.setCreatedAt(drop.getCreatedAt());
        dto.setUpdatedAt(drop.getUpdatedAt());
        return dto;
    }
}