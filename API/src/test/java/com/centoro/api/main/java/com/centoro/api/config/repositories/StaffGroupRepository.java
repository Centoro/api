package com.centoro.api.repositories;

import com.centoro.api.entities.StaffGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface StaffGroupRepository extends JpaRepository<StaffGroup, Long> {
    Optional<StaffGroup> findByGroupId(String groupId);
    List<StaffGroup> findByType(String type);
    List<StaffGroup> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT g FROM StaffGroup g WHERE g.members LIKE CONCAT('%', :staffId, '%')")
    List<StaffGroup> findByMemberId(String staffId);
}