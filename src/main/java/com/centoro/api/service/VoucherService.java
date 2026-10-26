package com.centoro.api.service;

import com.centoro.api.entities.Voucher;
import com.centoro.api.repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoucherService {
    
    @Autowired
    private VoucherRepository voucherRepository;
    
    @Transactional
    public Voucher createVoucher(Voucher voucher) {
        voucher.setVoucherId("VCH-" + String.format("%04d", System.currentTimeMillis() % 10000));
        voucher.setIssuedDate(new Date());
        voucher.setUpdatedAt(new Date());
        voucher.setUsedCount(0);
        voucher.setStatus("active");
        
        if (voucher.getMaxUses() == null) voucher.setMaxUses(10);
        if (voucher.getType() == null) voucher.setType("percent");
        if (voucher.getIsPublic() == null) voucher.setIsPublic(true);
        if (voucher.getPurpose() == null) voucher.setPurpose("standard");
        if (voucher.getCategory() == null) voucher.setCategory("All Services");
        
        return voucherRepository.save(voucher);
    }
    
    public List<Voucher> getAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        vouchers.removeIf(v -> v.getDeletedAt() != null);
        return vouchers;
    }
    
    public Optional<Voucher> getVoucherById(Long id) {
        return voucherRepository.findById(id);
    }
    
    public Optional<Voucher> getVoucherByCode(String code) {
        return voucherRepository.findByCode(code);
    }
    
    public Optional<Voucher> getVoucherByPromoCode(String promoCode) {
        return voucherRepository.findByPromoCode(promoCode);
    }
    
    public List<Voucher> getVouchersByClient(Long clientId) {
        return voucherRepository.findByClientId(clientId);
    }
    
    public List<Voucher> getActiveVouchers() {
        return voucherRepository.findActiveVouchers();
    }
    
    public List<Voucher> getVouchersByStatus(String status) {
        return voucherRepository.findByStatus(status);
    }
    
    public List<Voucher> getVouchersByCategory(String category) {
        return voucherRepository.findByCategory(category);
    }
    
    @Transactional
    public Voucher updateVoucher(Long id, Voucher voucher) {
        Voucher existing = voucherRepository.findById(id).orElseThrow();
        existing.setCode(voucher.getCode());
        existing.setPromoCode(voucher.getPromoCode());
        existing.setType(voucher.getType());
        existing.setValue(voucher.getValue());
        existing.setCategory(voucher.getCategory());
        existing.setClientId(voucher.getClientId());
        existing.setIsPublic(voucher.getIsPublic());
        existing.setMaxUses(voucher.getMaxUses());
        existing.setStartDate(voucher.getStartDate());
        existing.setExpiryDate(voucher.getExpiryDate());
        existing.setPurpose(voucher.getPurpose());
        existing.setRecipientCountry(voucher.getRecipientCountry());
        existing.setRecipientLabel(voucher.getRecipientLabel());
        existing.setOtherFirstName(voucher.getOtherFirstName());
        existing.setOtherSurname(voucher.getOtherSurname());
        existing.setOtherInitials(voucher.getOtherInitials());
        existing.setOtherPhone(voucher.getOtherPhone());
        existing.setOtherEmail(voucher.getOtherEmail());
        existing.setOtherAddress(voucher.getOtherAddress());
        existing.setCompany(voucher.getCompany());
        existing.setCompanyCK(voucher.getCompanyCK());
        existing.setCustomBg(voucher.getCustomBg());
        existing.setStatus(voucher.getStatus());
        existing.setUpdatedAt(new Date());
        existing.setUpdatedBy(voucher.getUpdatedBy());
        return voucherRepository.save(existing);
    }
    
    @Transactional
    public Voucher redeemVoucher(Long id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow();
        
        // Check if voucher is active
        if (!"active".equals(voucher.getStatus())) {
            throw new RuntimeException("Voucher is not active");
        }
        
        // Check if expired
        if (voucher.getExpiryDate() != null && voucher.getExpiryDate().before(new Date())) {
            voucher.setStatus("expired");
            return voucherRepository.save(voucher);
        }
        
        // Check max uses
        if (voucher.getUsedCount() >= voucher.getMaxUses()) {
            voucher.setStatus("expired");
            return voucherRepository.save(voucher);
        }
        
        voucher.setUsedCount(voucher.getUsedCount() + 1);
        if (voucher.getUsedCount() >= voucher.getMaxUses()) {
            voucher.setStatus("expired");
        }
        voucher.setUpdatedAt(new Date());
        return voucherRepository.save(voucher);
    }
    
    @Transactional
    public void softDeleteVoucher(Long id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow();
        voucher.setDeletedAt(new Date());
        voucherRepository.save(voucher);
    }
    
    public Map<String, Object> getVoucherStats() {
        List<Voucher> vouchers = getAllVouchers();
        
        long active = vouchers.stream()
            .filter(v -> "active".equals(v.getStatus()))
            .filter(v -> v.getExpiryDate() == null || v.getExpiryDate().after(new Date()))
            .filter(v -> v.getUsedCount() < v.getMaxUses())
            .count();
        
        long expired = vouchers.stream()
            .filter(v -> !"active".equals(v.getStatus()) || 
                (v.getExpiryDate() != null && v.getExpiryDate().before(new Date())) ||
                v.getUsedCount() >= v.getMaxUses())
            .count();
        
        long totalUsedCount = vouchers.stream()
            .mapToInt(v -> v.getUsedCount() != null ? v.getUsedCount() : 0)
            .sum();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("active", active);
        stats.put("usedCount", totalUsedCount);
        stats.put("expired", expired);
        stats.put("total", (long) vouchers.size());
        
        return stats;
    }
    
    public List<String> getVoucherCategories() {
        List<Voucher> vouchers = getAllVouchers();
        return vouchers.stream()
            .map(Voucher::getCategory)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }
    
    public List<String> getVoucherTypes() {
        List<Voucher> vouchers = getAllVouchers();
        return vouchers.stream()
            .map(Voucher::getType)
            .filter(Objects::nonNull)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }
    
    @Transactional
    public Voucher duplicateVoucher(Long id) {
        Voucher original = voucherRepository.findById(id).orElseThrow();
        
        Voucher copy = new Voucher();
        copy.setCode(generateUniqueCode());
        copy.setPromoCode(original.getPromoCode() != null ? original.getPromoCode() + "-COPY" : null);
        copy.setType(original.getType());
        copy.setValue(original.getValue());
        copy.setCategory(original.getCategory());
        copy.setClientId(original.getClientId());
        copy.setIsPublic(original.getIsPublic());
        copy.setMaxUses(original.getMaxUses());
        copy.setStartDate(original.getStartDate());
        copy.setExpiryDate(original.getExpiryDate());
        copy.setPurpose(original.getPurpose());
        copy.setRecipientCountry(original.getRecipientCountry());
        copy.setRecipientLabel(original.getRecipientLabel());
        copy.setOtherFirstName(original.getOtherFirstName());
        copy.setOtherSurname(original.getOtherSurname());
        copy.setOtherInitials(original.getOtherInitials());
        copy.setOtherPhone(original.getOtherPhone());
        copy.setOtherEmail(original.getOtherEmail());
        copy.setOtherAddress(original.getOtherAddress());
        copy.setCompany(original.getCompany());
        copy.setCompanyCK(original.getCompanyCK());
        copy.setCustomBg(original.getCustomBg());
        copy.setStatus("active");
        copy.setIssuedDate(new Date());
        copy.setIssuedBy(original.getIssuedBy());
        copy.setUsedCount(0);
        // Voucher entity uses issuedDate, not createdAt
        copy.setUpdatedAt(new Date());
        
        return voucherRepository.save(copy);
    }
    
    private String generateUniqueCode() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        String code;
        do {
            code = "CENTORO-" + java.util.stream.IntStream.range(0, 6)
                .mapToObj(i -> String.valueOf(chars.charAt(new Random().nextInt(chars.length()))))
                .collect(java.util.stream.Collectors.joining());
        } while (voucherRepository.findByCode(code).isPresent());
        return code;
    }
}