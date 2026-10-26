package com.centoro.api.repositories;

import com.centoro.api.entities.LinkedAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LinkedAccountRepository extends JpaRepository<LinkedAccount, Long> {
    List<LinkedAccount> findByPlatform(String platform);
    List<LinkedAccount> findByVisibleTrue();
}