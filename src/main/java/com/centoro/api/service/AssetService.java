package com.centoro.api.service;

import com.centoro.api.entities.Asset;
import com.centoro.api.entities.AssetBooking;
import com.centoro.api.repositories.AssetRepository;
import com.centoro.api.repositories.AssetBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;
    
    @Autowired
    private AssetBookingRepository assetBookingRepository;

    @Transactional
    public Asset createAsset(Asset asset) {
        asset.setAssetId("AST-" + String.format("%04d", System.currentTimeMillis() % 10000));
        asset.setCreatedAt(new Date());
        asset.setUpdatedAt(new Date());
        if (asset.getStatus() == null) asset.setStatus("active");
        if (asset.getCost() == null) asset.setCost(0.0);
        if (asset.getSalvage() == null) asset.setSalvage(0.0);
        if (asset.getLifeYears() == null) asset.setLifeYears(3);
        return assetRepository.save(asset);
    }

    public List<Asset> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        assets.removeIf(a -> a.getDeletedAt() != null);
        return assets;
    }

    public Optional<Asset> getAssetById(Long id) {
        return assetRepository.findById(id);
    }

    public List<Asset> getAvailableAssets() {
        List<Asset> assets = getAllAssets();
        List<Long> bookedAssetIds = assetBookingRepository.findByReturnedDateIsNull().stream()
            .map(AssetBooking::getAssetId)
            .collect(java.util.stream.Collectors.toList());
        
        assets.removeIf(a -> bookedAssetIds.contains(a.getId()));
        return assets;
    }

    @Transactional
    public Asset updateAsset(Long id, Asset asset) {
        Asset existing = assetRepository.findById(id).orElseThrow();
        existing.setName(asset.getName());
        existing.setCategory(asset.getCategory());
        existing.setPurchaseDate(asset.getPurchaseDate());
        existing.setCost(asset.getCost());
        existing.setSalvage(asset.getSalvage());
        existing.setLifeYears(asset.getLifeYears());
        existing.setStatus(asset.getStatus());
        existing.setUpdatedAt(new Date());
        return assetRepository.save(existing);
    }

    @Transactional
    public void softDeleteAsset(Long id) {
        Asset asset = assetRepository.findById(id).orElseThrow();
        asset.setDeletedAt(new Date());
        assetRepository.save(asset);
    }

    @Transactional
    public AssetBooking bookOutAsset(AssetBooking booking) {
        // Validate asset exists and is available
        Optional<Asset> assetOpt = assetRepository.findById(booking.getAssetId());
        if (assetOpt.isEmpty()) {
            throw new RuntimeException("Asset not found");
        }
        
        Asset asset = assetOpt.get();
        
        // Check if already booked
        List<AssetBooking> existingBookings = assetBookingRepository.findByAssetId(booking.getAssetId());
        boolean isBooked = existingBookings.stream().anyMatch(b -> b.getReturnedDate() == null);
        if (isBooked) {
            throw new RuntimeException("Asset is already booked out");
        }
        
        booking.setBookingId("BKG-" + String.format("%04d", System.currentTimeMillis() % 10000));
        booking.setCreatedAt(new Date());
        booking.setUpdatedAt(new Date());
        if (booking.getOutDate() == null) booking.setOutDate(new Date());
        
        // Update asset status
        asset.setStatus("booked");
        assetRepository.save(asset);
        
        return assetBookingRepository.save(booking);
    }

    @Transactional
    public AssetBooking returnAsset(String bookingId) {
        Optional<AssetBooking> bookingOpt = assetBookingRepository.findByBookingId(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new RuntimeException("Booking not found");
        }
        
        AssetBooking booking = bookingOpt.get();
        booking.setReturnedDate(new Date());
        booking.setUpdatedAt(new Date());
        assetBookingRepository.save(booking);
        
        // Update asset status
        Optional<Asset> assetOpt = assetRepository.findById(booking.getAssetId());
        assetOpt.ifPresent(asset -> {
            asset.setStatus("active");
            assetRepository.save(asset);
        });
        
        return booking;
    }

    public List<AssetBooking> getActiveBookings() {
        return assetBookingRepository.findByReturnedDateIsNull();
    }

    public List<AssetBooking> getBookingsByAsset(Long assetId) {
        return assetBookingRepository.findByAssetId(assetId);
    }

    // ============ DEPRECIATION CALCULATION ============
    
    public Map<String, Object> calculateDepreciation(Long assetId) {
        Optional<Asset> assetOpt = assetRepository.findById(assetId);
        if (assetOpt.isEmpty()) {
            return Collections.emptyMap();
        }
        return calculateDepreciation(assetOpt.get());
    }
    
    public Map<String, Object> calculateDepreciation(Asset asset) {
        Map<String, Object> result = new HashMap<>();
        
        double cost = asset.getCost() != null ? asset.getCost() : 0.0;
        double salvage = asset.getSalvage() != null ? asset.getSalvage() : 0.0;
        int lifeYears = asset.getLifeYears() != null ? asset.getLifeYears() : 3;
        Date purchaseDate = asset.getPurchaseDate();
        
        double depreciableAmount = cost - salvage;
        double annualDepreciation = lifeYears > 0 ? depreciableAmount / lifeYears : 0;
        double monthlyDepreciation = annualDepreciation / 12;
        
        // Calculate accumulated depreciation
        double accumulated = 0.0;
        double bookValue = cost;
        int monthsOwned = 0;
        
        if (purchaseDate != null) {
            LocalDate purchase = purchaseDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate now = LocalDate.now();
            monthsOwned = (int) ChronoUnit.MONTHS.between(purchase, now);
            accumulated = Math.min(monthlyDepreciation * monthsOwned, depreciableAmount);
            bookValue = cost - accumulated;
        }
        
        double depreciationPct = cost > 0 ? (accumulated / cost) * 100 : 0;
        
        result.put("cost", cost);
        result.put("salvage", salvage);
        result.put("lifeYears", lifeYears);
        result.put("depreciableAmount", depreciableAmount);
        result.put("annualDepreciation", annualDepreciation);
        result.put("monthlyDepreciation", monthlyDepreciation);
        result.put("monthsOwned", monthsOwned);
        result.put("accumulatedDepreciation", accumulated);
        result.put("bookValue", Math.max(bookValue, 0));
        result.put("depreciationPct", Math.min(depreciationPct, 100));
        
        return result;
    }

    public Map<String, Object> getAssetStats() {
        List<Asset> assets = getAllAssets();
        List<AssetBooking> activeBookings = getActiveBookings();
        
        double totalCost = assets.stream()
            .mapToDouble(a -> a.getCost() != null ? a.getCost() : 0.0)
            .sum();
        
        double totalBookValue = 0.0;
        for (Asset asset : assets) {
            Map<String, Object> dep = calculateDepreciation(asset);
            totalBookValue += (double) dep.get("bookValue");
        }
        
        long available = assets.stream()
            .filter(a -> activeBookings.stream().noneMatch(b -> b.getAssetId().equals(a.getId())))
            .count();
        
        long bookedOut = activeBookings.size();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCost", totalCost);
        stats.put("totalBookValue", totalBookValue);
        stats.put("available", available);
        stats.put("bookedOut", bookedOut);
        stats.put("totalAssets", (long) assets.size());
        
        return stats;
    }
}