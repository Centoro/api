package com.centoro.api.repositories;

import com.centoro.api.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByContractId(String contractId);
    List<Contract> findByCategory(String category);
    List<Contract> findByNameContainingIgnoreCase(String name);
}