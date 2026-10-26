package com.centoro.api.controller;

import com.centoro.api.dto.BinItemDTO;
import com.centoro.api.entities.BinItem;
import com.centoro.api.service.BinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bin")
@CrossOrigin(origins = "*")
public class BinController {

    @Autowired
    private BinService binService;

    @GetMapping
    public ResponseEntity<?> getAllBinItems() {
        List<BinItem> binItems = binService.getAllBinItems();
        List<BinItemDTO> binItemDTOs = binItems.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", binItemDTOs
        ));
    }

    @GetMapping("/collection/{collection}")
    public ResponseEntity<?> getBinItemsByCollection(@PathVariable String collection) {
        List<BinItem> binItems = binService.getBinItemsByCollection(collection);
        List<BinItemDTO> binItemDTOs = binItems.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", binItemDTOs
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBinItem(@PathVariable Long id) {
        Optional<BinItem> binItemOpt = binService.getBinItemById(id);
        if (binItemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(binItemOpt.get())
        ));
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<?> restoreBinItem(@PathVariable Long id) {
        Optional<BinItem> binItemOpt = binService.getBinItemById(id);
        if (binItemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        binService.restoreBinItem(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Item restored successfully"
        ));
    }

    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<?> permanentDelete(@PathVariable Long id) {
        Optional<BinItem> binItemOpt = binService.getBinItemById(id);
        if (binItemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        binService.deleteBinItem(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Item permanently deleted"
        ));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<?> emptyBin() {
        binService.emptyBin();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Bin emptied successfully"
        ));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkInBin(
            @RequestParam String collection,
            @RequestParam Long originalId) {
        boolean inBin = binService.isInBin(collection, originalId);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", Map.of(
                "collection", collection,
                "originalId", originalId,
                "inBin", inBin
            )
        ));
    }

    private BinItemDTO convertToDTO(BinItem binItem) {
        BinItemDTO dto = new BinItemDTO();
        dto.setId(binItem.getId());
        dto.setBinId(binItem.getBinId());
        dto.setCollection(binItem.getCollection());
        dto.setOriginalId(binItem.getOriginalId());
        dto.setSnapshot(binItem.getSnapshot());
        dto.setDeletedBy(binItem.getDeletedBy());
        dto.setDeletedAt(binItem.getDeletedAt());
        dto.setCreatedAt(binItem.getCreatedAt());
        return dto;
    }
}