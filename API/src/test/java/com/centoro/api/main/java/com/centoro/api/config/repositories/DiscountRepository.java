package com.centoro.api.repositories;

import com.centoro.api.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    
    Optional<Discount> findByDiscountId(String discountId);
    List<Discount> findByClientId(Long clientId);
    List<Discount> findBySource(String source);
}