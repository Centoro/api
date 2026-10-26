package com.centoro.api.controller;

import com.centoro.api.entities.Asset;
import com.centoro.api.entities.AssetBooking;
import com.centoro.api.repositories.AssetRepository;
import com.centoro.api.repositories.AssetBookingRepository;
import com.centoro.api.repositories.StaffRepository;
import com.centoro.api.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "*")
public class AssetsController {

    @Autowired
    private AssetRepository assetRepository;
    
    @Autowired
    private AssetBookingRepository assetBookingRepository;
    
    @Autowired
    private StaffRepository staffRepository;
    
    @Autowired
    private AssetService assetService;

    // ============ ASSET CRUD ============
    
    @GetMapping
    public ResponseEntity<?> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        assets.removeIf(a -> a.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", assets
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAsset(@PathVariable Long id) {
        Optional<Asset> asset = assetRepository.findById(id);
        if (asset.isEmpty() || asset.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", asset.get()
        ));
    }

    @PostMapping
    public ResponseEntity<?> createAsset(@RequestBody Asset asset) {
        asset.setAssetId("AST-" + String.format("%04d", System.currentTimeMillis() % 10000));
        asset.setCreatedAt(new Date());
        asset.setUpdatedAt(new Date());
        asset.setStatus("active");
        
        if (asset.getCost() == null) asset.setCost(0.0);
        if (asset.getSalvage() == null) asset.setSalvage(0.0);
        if (asset.getLifeYears() == null) asset.setLifeYears(3);
        
        Asset saved = assetRepository.save(asset);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Asset created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAsset(@PathVariable Long id, @RequestBody Asset asset) {
        Optional<Asset> existingOpt = assetRepository.findById(id);
        if (existingOpt.isEmpty() || existingOpt.get().getDeletedAt() != null) {
            return ResponseEntity.notFound().build();
        }
        
        Asset existing = existingOpt.get();
        existing.setName(asset.getName());
        existing.setCategory(asset.getCategory());
        existing.setPurchaseDate(asset.getPurchaseDate());
        existing.setCost(asset.getCost());
        existing.setSalvage(asset.getSalvage());
        existing.setLifeYears(asset.getLifeYears());
        existing.setStatus(asset.getStatus());
        existing.setUpdatedAt(new Date());
        
        Asset updated = assetRepository.save(existing);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Asset updated successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAsset(@PathVariable Long id) {
        Optional<Asset> assetOpt = assetRepository.findById(id);
        if (assetOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Asset asset = assetOpt.get();
        asset.setDeletedAt(new Date());
        assetRepository.save(asset);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Asset deleted successfully"
        ));
    }

    // ============ BOOKINGS ============
    
    @GetMapping("/bookings")
    public ResponseEntity<?> getAllBookings() {
        List<AssetBooking> bookings = assetBookingRepository.findAll();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", bookings
        ));
    }

    @GetMapping("/bookings/active")
    public ResponseEntity<?> getActiveBookings() {
        List<AssetBooking> bookings = assetBookingRepository.findByReturnedDateIsNull();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", bookings
        ));
    }

    @GetMapping("/{assetId}/bookings")
    public ResponseEntity<?> getAssetBookings(@PathVariable Long assetId) {
        List<AssetBooking> bookings = assetBookingRepository.findByAssetId(assetId);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", bookings
        ));
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(@RequestBody AssetBooking booking) {
        // Validate asset exists
        if (!assetRepository.existsById(booking.getAssetId())) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Asset not found"
            ));
        }
        
        // Validate staff exists
        if (!staffRepository.existsById(booking.getStaffId())) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Staff not found"
            ));
        }
        
        // Check if asset is already booked
        List<AssetBooking> existingBookings = assetBookingRepository.findByAssetId(booking.getAssetId());
        boolean isBooked = existingBookings.stream().anyMatch(b -> b.getReturnedDate() == null);
        if (isBooked) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Asset is already booked out"
            ));
        }
        
        booking.setBookingId("BKG-" + String.format("%04d", System.currentTimeMillis() % 10000));
        booking.setCreatedAt(new Date());
        booking.setUpdatedAt(new Date());
        
        if (booking.getOutDate() == null) booking.setOutDate(new Date());
        
        // Update asset status
        Asset asset = assetRepository.findById(booking.getAssetId()).get();
        asset.setStatus("booked");
        assetRepository.save(asset);
        
        AssetBooking saved = assetBookingRepository.save(booking);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Asset booked successfully"
        ));
    }

    @PutMapping("/bookings/{bookingId}/return")
    public ResponseEntity<?> returnAsset(@PathVariable String bookingId) {
        Optional<AssetBooking> bookingOpt = assetBookingRepository.findByBookingId(bookingId);
        if (bookingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        AssetBooking booking = bookingOpt.get();
        booking.setReturnedDate(new Date());
        booking.setUpdatedAt(new Date());
        assetBookingRepository.save(booking);
        
        // Update asset status
        Asset asset = assetRepository.findById(booking.getAssetId()).get();
        asset.setStatus("active");
        assetRepository.save(asset);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", booking,
            "message", "Asset returned successfully"
        ));
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        if (!assetBookingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        assetBookingRepository.deleteById(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Booking deleted successfully"
        ));
    }

    // ============ STATS ============
    
    @GetMapping("/stats")
    public ResponseEntity<?> getAssetStats() {
        Map<String, Object> stats = assetService.getAssetStats();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", stats
        ));
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableAssets() {
        List<Asset> assets = assetService.getAvailableAssets();
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", assets
        ));
    }

    @GetMapping("/{id}/depreciation")
    public ResponseEntity<?> getDepreciation(@PathVariable Long id) {
        Optional<Asset> assetOpt = assetService.getAssetById(id);
        if (assetOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> depreciation = assetService.calculateDepreciation(assetOpt.get());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", depreciation
        ));
    }
}