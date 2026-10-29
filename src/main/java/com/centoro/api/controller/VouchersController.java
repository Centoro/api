package com.centoro.api.controller;

import com.centoro.api.entities.Voucher;
import com.centoro.api.repositories.VoucherRepository;
import com.centoro.api.repositories.ClientRepository;
import com.centoro.api.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/vouchers")
@CrossOrigin(origins = "*")
public class VouchersController {

    @Autowired
    private VoucherRepository voucherRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public ResponseEntity<?> getAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        vouchers.removeIf(v -> v.getDeletedAt() != null);
        return ResponseEntity.ok(Map.of("success", true, "data", vouchers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVoucher(@PathVariable Long id) {
        Optional<Voucher> voucher = voucherRepository.findById(id);
        if (voucher.isEmpty() || voucher.get().getDeletedAt() != null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", voucher.get()));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getVoucherByCode(@PathVariable String code) {
        Optional<Voucher> voucher = voucherRepository.findByCode(code.toUpperCase());
        if (voucher.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", voucher.get()));
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveVouchers() {
        List<Voucher> vouchers = voucherRepository.findActiveVouchers();
        return ResponseEntity.ok(Map.of("success", true, "data", vouchers));
    }

    @PostMapping
    public ResponseEntity<?> createVoucher(@RequestBody Voucher voucher) {
        if (voucher.getClientId() != null && !clientRepository.existsById(voucher.getClientId())) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Client not found"));
        }
        Voucher saved = voucherService.createVoucher(voucher);
        return ResponseEntity.ok(Map.of("success", true, "data", saved, "message", "Voucher created successfully"));
    }

    @PostMapping("/{id}/redeem")
    public ResponseEntity<?> redeemVoucher(@PathVariable Long id) {
        try {
            Voucher updated = voucherService.redeemVoucher(id);
            return ResponseEntity.ok(Map.of("success", true, "data", updated, "message", "Voucher redeemed successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable Long id, @RequestBody Voucher voucher) {
        try {
            Voucher updated = voucherService.updateVoucher(id, voucher);
            return ResponseEntity.ok(Map.of("success", true, "data", updated, "message", "Voucher updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable Long id) {
        try {
            voucherService.softDeleteVoucher(id);
            return ResponseEntity.ok(Map.of("success", true, "message", "Voucher deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getVoucherStats() {
        Map<String, Object> stats = voucherService.getVoucherStats();
        return ResponseEntity.ok(Map.of("success", true, "data", stats));
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getVoucherCategories() {
        List<String> categories = voucherService.getVoucherCategories();
        return ResponseEntity.ok(Map.of("success", true, "data", categories));
    }

    @GetMapping("/types")
    public ResponseEntity<?> getVoucherTypes() {
        List<String> types = voucherService.getVoucherTypes();
        return ResponseEntity.ok(Map.of("success", true, "data", types));
    }

    @GetMapping("/promo/{promoCode}")
    public ResponseEntity<?> getVoucherByPromoCode(@PathVariable String promoCode) {
        Optional<Voucher> voucher = voucherService.getVoucherByPromoCode(promoCode);
        if (voucher.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("success", true, "data", voucher.get()));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getVouchersByStatus(@PathVariable String status) {
        List<Voucher> vouchers = voucherService.getVouchersByStatus(status);
        return ResponseEntity.ok(Map.of("success", true, "data", vouchers));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getVouchersByCategory(@PathVariable String category) {
        List<Voucher> vouchers = voucherService.getVouchersByCategory(category);
        return ResponseEntity.ok(Map.of("success", true, "data", vouchers));
    }

    @PostMapping("/{id}/duplicate")
    public ResponseEntity<?> duplicateVoucher(@PathVariable Long id) {
        try {
            Voucher duplicated = voucherService.duplicateVoucher(id);
            return ResponseEntity.ok(Map.of("success", true, "data", duplicated, "message", "Voucher duplicated successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/validate/{code}")
    public ResponseEntity<?> validateVoucher(@PathVariable String code) {
        Optional<Voucher> voucherOpt = voucherService.getVoucherByCode(code);
        if (voucherOpt.isEmpty()) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Voucher not found", "valid", false));
        }
        
        Voucher voucher = voucherOpt.get();
        boolean isValid = "active".equals(voucher.getStatus()) &&
            (voucher.getExpiryDate() == null || voucher.getExpiryDate().after(new Date())) &&
            voucher.getUsedCount() < voucher.getMaxUses();
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("valid", isValid);
        result.put("voucher", voucher);
        
        if (!isValid) {
            if (!"active".equals(voucher.getStatus())) result.put("reason", "Voucher is not active");
            else if (voucher.getExpiryDate() != null && voucher.getExpiryDate().before(new Date())) result.put("reason", "Voucher has expired");
            else if (voucher.getUsedCount() >= voucher.getMaxUses()) result.put("reason", "Voucher has reached maximum uses");
        }
        
        return ResponseEntity.ok(result);
    }
}
