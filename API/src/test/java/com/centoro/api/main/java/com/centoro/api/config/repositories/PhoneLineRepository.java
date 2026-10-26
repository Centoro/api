package com.centoro.api.repositories;

import com.centoro.api.entities.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PhoneLineRepository extends JpaRepository<PhoneLine, Long> {
    Optional<PhoneLine> findByLineId(String lineId);
    List<PhoneLine> findByStatus(String status);
    Optional<PhoneLine> findByNumber(String number);
}