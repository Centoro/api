package com.centoro.api.controller;

import com.centoro.api.entities.WebsitePriceList;
import com.centoro.api.repositories.WebsitePriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/website/prices")
@CrossOrigin(origins = "*")
public class WebsitePriceListController {

    @Autowired
    private WebsitePriceListRepository websitePriceListRepository;

    @GetMapping
    public ResponseEntity<?> getAllPrices() {
        List<WebsitePriceList> prices = websitePriceListRepository.findAll();
        prices.removeIf(p -> p.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", prices
        ));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getPricesByType(@PathVariable String type) {
        List<WebsitePriceList> prices = websitePriceListRepository.findByType(type);
        prices.removeIf(p -> p.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", prices
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrice(@PathVariable Long id) {
        Optional<WebsitePriceList> priceOpt = websitePriceListRepository.findById(id);
        if (priceOpt.isEmpty() || priceOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", priceOpt.get()
        ));
    }

    @PostMapping
    public ResponseEntity<?> createPrice(@RequestBody WebsitePriceList price) {
        price.setPriceListId("PRC-" + String.format("%04d", System.currentTimeMillis() % 10000));
        price.setCreatedAt(new Date());
        price.setUpdatedAt(new Date());
        if (price.getPrice() == null) price.setPrice(0.0);
        if (price.getType() == null) price.setType("project");
        
        WebsitePriceList saved = websitePriceListRepository.save(price);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Price added successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrice(@PathVariable Long id, @RequestBody WebsitePriceList price) {
        Optional<WebsitePriceList> existingOpt = websitePriceListRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        WebsitePriceList existing = existingOpt.get();
        existing.setService(price.getService());
        existing.setPrice(price.getPrice());
        existing.setType(price.getType());
        existing.setUpdatedAt(new Date());
        
        WebsitePriceList updated = websitePriceListRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Price updated successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrice(@PathVariable Long id) {
        Optional<WebsitePriceList> priceOpt = websitePriceListRepository.findById(id);
        if (priceOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        WebsitePriceList price = priceOpt.get();
        price.setDeletedAt(new Date());
        websitePriceListRepository.save(price);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Price deleted successfully"
        ));
    }
}