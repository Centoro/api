package com.centoro.api.controller;

import com.centoro.api.dto.InventoryDTO;
import com.centoro.api.entities.Inventory;
import com.centoro.api.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<?> getAllItems() {
        List<Inventory> items = inventoryService.getAllItems();
        List<InventoryDTO> itemDTOs = items.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", itemDTOs
        ));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getItemsByCategory(@PathVariable String category) {
        List<Inventory> items = inventoryService.getItemsByCategory(category);
        List<InventoryDTO> itemDTOs = items.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", itemDTOs
        ));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<?> getLowStockItems() {
        List<Inventory> items = inventoryService.getItemsNeedingReorder();
        List<InventoryDTO> itemDTOs = items.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", itemDTOs
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable Long id) {
        Optional<Inventory> itemOpt = inventoryService.getItemById(id);
        if (itemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(itemOpt.get())
        ));
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<?> getItemBySku(@PathVariable String sku) {
        Optional<Inventory> itemOpt = inventoryService.getItemBySku(sku);
        if (itemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(itemOpt.get())
        ));
    }

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody Inventory item) {
        Inventory saved = inventoryService.createItem(item);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Inventory item created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Inventory item) {
        try {
            Inventory updated = inventoryService.updateItem(id, item);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Inventory item updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        Integer qtyChange = request.get("qtyChange");
        if (qtyChange == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "qtyChange is required"
            ));
        }
        
        try {
            Inventory updated = inventoryService.updateStock(id, qtyChange);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Stock updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        if (!inventoryService.getItemById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        inventoryService.deleteItem(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Inventory item deleted successfully"
        ));
    }

    private InventoryDTO convertToDTO(Inventory item) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(item.getId());
        dto.setItemId(item.getItemId());
        dto.setName(item.getName());
        dto.setSku(item.getSku());
        dto.setCategory(item.getCategory());
        dto.setQty(item.getQty());
        dto.setCost(item.getCost());
        dto.setPrice(item.getPrice());
        dto.setReorderLevel(item.getReorderLevel());
        dto.setCreatedAt(item.getCreatedAt());
        dto.setUpdatedAt(item.getUpdatedAt());
        return dto;
    }
}