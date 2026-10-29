package com.centoro.api.repositories;

import com.centoro.api.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    // FIX: Changed from Long to String
    List<Discount> findByClientId(String clientId);
}
