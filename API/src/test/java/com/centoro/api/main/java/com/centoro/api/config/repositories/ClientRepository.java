package com.centoro.api.repositories;

import com.centoro.api.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    
    Optional<Client> findByClientId(String clientId);
    Optional<Client> findByCode(String code);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByPhone(String phone);
    
    List<Client> findByNameContainingIgnoreCase(String name);
    List<Client> findByStatus(Client.ClientStatus status);
    List<Client> findByCompanyNameContainingIgnoreCase(String companyName);
    
    @Query("SELECT c FROM Client c WHERE c.deletedAt IS NULL")
    List<Client> findAllActive();
    
    @Query("SELECT c FROM Client c WHERE c.deletedAt IS NOT NULL")
    List<Client> findAllDeleted();
    
    boolean existsByClientId(String clientId);
    boolean existsByCode(String code);
    boolean existsByEmail(String email);
}

