package com.centoro.api.repositories;

import com.centoro.api.entities.MonthlyOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface MonthlyOfferRepository extends JpaRepository<MonthlyOffer, Long> {
    
    Optional<MonthlyOffer> findByOfferId(String offerId);
    List<MonthlyOffer> findByStatus(String status);
    List<MonthlyOffer> findByMonth(String month);
    
    @Query("SELECT m FROM MonthlyOffer m WHERE m.status = 'live' AND m.date <= CURRENT_DATE AND (m.expiry IS NULL OR m.expiry >= CURRENT_DATE)")
    List<MonthlyOffer> findActiveOffers();
}