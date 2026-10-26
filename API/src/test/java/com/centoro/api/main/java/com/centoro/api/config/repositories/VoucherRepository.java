package com.centoro.api.repositories;

import com.centoro.api.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    
    Optional<Voucher> findByVoucherId(String voucherId);
    Optional<Voucher> findByCode(String code);
    Optional<Voucher> findByPromoCode(String promoCode);
    
    List<Voucher> findByClientId(Long clientId);
    List<Voucher> findByStatus(String status);
    List<Voucher> findByCategory(String category);
    
    @Query("SELECT v FROM Voucher v WHERE v.status = 'active' AND v.expiryDate >= CURRENT_DATE AND (v.usedCount IS NULL OR v.usedCount < v.maxUses)")
    List<Voucher> findActiveVouchers();
}