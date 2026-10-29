package com.centoro.api.repositories;

import com.centoro.api.entities.StaffFile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StaffFileRepository extends JpaRepository<StaffFile, Long> {
    Optional<StaffFile> findByFileId(String fileId);
    
    // ✅ FIX: Changed from Long to String
    List<StaffFile> findByStaffId(String staffId);
    
    List<StaffFile> findByCategory(String category);
}
