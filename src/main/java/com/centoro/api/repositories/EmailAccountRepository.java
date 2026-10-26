package com.centoro.api.repositories;

import com.centoro.api.entities.EmailAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmailAccountRepository extends JpaRepository<EmailAccount, Long> {
    Optional<EmailAccount> findByAccountId(String accountId);
    List<EmailAccount> findByType(String type);
    List<EmailAccount> findByStatus(String status);
    Optional<EmailAccount> findByEmail(String email);
}