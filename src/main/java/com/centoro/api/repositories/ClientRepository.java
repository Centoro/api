package com.centoro.api.repositories;

import com.centoro.api.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByCode(String code);
    Optional<Client> findByEmail(String email);
    List<Client> findByStatus(Client.ClientStatus status);
    
    @Query("SELECT c FROM Client c WHERE c.deletedAt IS NULL")
    List<Client> findAllActive();
    
    @Query("SELECT c FROM Client c WHERE c.deletedAt IS NULL AND " +
           "(LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.code) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Client> searchClients(@Param("query") String query);
}
