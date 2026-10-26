package com.centoro.api.repositories;

import com.centoro.api.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    
    Optional<Staff> findByStaffId(String staffId);
    Optional<Staff> findByUsername(String username);
    Optional<Staff> findByEmail(String email);
    
    List<Staff> findByStatus(Staff.StaffStatus status);
    List<Staff> findByRole(Staff.StaffRole role);
    
    // FIXED: Remove ContainingIgnoreCase for enum fields
    // Use these instead:
    List<Staff> findByFirstNameContainingIgnoreCase(String firstName);
    List<Staff> findByLastNameContainingIgnoreCase(String lastName);
    
    // For role search, use @Query or exact match
    @Query("SELECT s FROM Staff s WHERE LOWER(s.role) LIKE LOWER(CONCAT('%', :role, '%'))")
    List<Staff> findByRoleContainingIgnoreCase(@Param("role") String role);
    
    default List<Staff> findAllActive() {
        return findByStatus(Staff.StaffStatus.active);
    }
    
    @Query("SELECT s FROM Staff s WHERE s.deletedAt IS NOT NULL")
    List<Staff> findAllDeleted();
    
    boolean existsByStaffId(String staffId);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}