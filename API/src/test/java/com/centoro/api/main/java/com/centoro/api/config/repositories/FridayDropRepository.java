package com.centoro.api.repositories;

import com.centoro.api.entities.FridayDrop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface FridayDropRepository extends JpaRepository<FridayDrop, Long> {
    
    Optional<FridayDrop> findByDropId(String dropId);
    List<FridayDrop> findByStatus(String status);
    List<FridayDrop> findByGenre(String genre);
    
    @Query("SELECT f FROM FridayDrop f WHERE f.status = 'live' AND f.date <= CURRENT_DATE AND (f.expiry IS NULL OR f.expiry >= CURRENT_DATE)")
    List<FridayDrop> findActiveDrops();
}