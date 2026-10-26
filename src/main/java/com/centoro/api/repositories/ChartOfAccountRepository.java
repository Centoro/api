package com.centoro.api.repositories;

import com.centoro.api.entities.ChartOfAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ChartOfAccountRepository extends JpaRepository<ChartOfAccount, Long> {
    Optional<ChartOfAccount> findByCode(String code);
    List<ChartOfAccount> findByType(String type);
    List<ChartOfAccount> findByNameContainingIgnoreCase(String name);
}