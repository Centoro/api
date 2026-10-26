package com.centoro.api.repositories;

import com.centoro.api.entities.PendingApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingApprovalRepository extends JpaRepository<PendingApproval, Long> {
}