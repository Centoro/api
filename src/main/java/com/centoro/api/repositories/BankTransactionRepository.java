package com.centoro.api.repositories;

import com.centoro.api.entities.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    
    Optional<BankTransaction> findByBankId(String bankId);
    List<BankTransaction> findByReconciled(Boolean reconciled);
    List<BankTransaction> findByDateBetween(java.util.Date start, java.util.Date end);
}