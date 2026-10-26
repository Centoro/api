package com.centoro.api.repositories;

import com.centoro.api.entities.PhoneBookContact;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PhoneBookContactRepository extends JpaRepository<PhoneBookContact, Long> {
    Optional<PhoneBookContact> findByContactId(String contactId);
    List<PhoneBookContact> findByCategory(String category);
    List<PhoneBookContact> findByClientId(Long clientId);
    List<PhoneBookContact> findBySpeedDialNotNullOrderBySpeedDialAsc();
    Optional<PhoneBookContact> findBySpeedDial(Integer speedDial);
}