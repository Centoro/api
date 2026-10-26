package com.centoro.api.repositories;

import com.centoro.api.entities.StaffFile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StaffFileRepository extends JpaRepository<StaffFile, Long> {
    Optional<StaffFile> findByFileId(String fileId);
    List<StaffFile> findByStaffId(Long staffId);
    List<StaffFile> findByCategory(String category);
}