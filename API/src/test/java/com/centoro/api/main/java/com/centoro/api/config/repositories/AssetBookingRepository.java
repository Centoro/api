package com.centoro.api.repositories;

import com.centoro.api.entities.AssetBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AssetBookingRepository extends JpaRepository<AssetBooking, Long> {
    
    Optional<AssetBooking> findByBookingId(String bookingId);
    List<AssetBooking> findByAssetId(Long assetId);
    List<AssetBooking> findByStaffId(Long staffId);
    List<AssetBooking> findByReturnedDateIsNull();
    List<AssetBooking> findByReturnedDateIsNotNull();
}