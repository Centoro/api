package com.centoro.api.repositories;

import com.centoro.api.entities.BinItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface BinRepository extends JpaRepository<BinItem, Long> {
    Optional<BinItem> findByBinId(String binId);
    List<BinItem> findByCollection(String collection);
    List<BinItem> findByOrderByDeletedAtDesc();
    
    @Modifying
    @Transactional
    @Query("DELETE FROM BinItem b WHERE b.collection = :collection AND b.originalId = :originalId")
    void deleteByCollectionAndOriginalId(String collection, Long originalId);
}