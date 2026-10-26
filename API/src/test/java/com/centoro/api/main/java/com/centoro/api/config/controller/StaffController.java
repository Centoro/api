package com.centoro.api.controller;

import com.centoro.api.entities.Staff;
import com.centoro.api.repositories.StaffRepository;
import com.centoro.api.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;
    
    @Autowired
    private IdGenerator idGenerator;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> getAllStaff() {
        List<Staff> staff = staffRepository.findAllActive();
        staff.forEach(s -> s.setPasswordHash(null));
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", staff
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStaff(@PathVariable Long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        if (staff.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        staff.get().setPasswordHash(null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", staff.get()
        ));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getStaffByUsername(@PathVariable String username) {
        Optional<Staff> staff = staffRepository.findByUsername(username);
        if (staff.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        staff.get().setPasswordHash(null);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", staff.get()
        ));
    }

    @PostMapping
    public ResponseEntity<?> createStaff(@RequestBody Staff staff) {
        staff.setStaffId(idGenerator.generateStaffId());
        staff.setCreatedAt(new Date());
        staff.setUpdatedAt(new Date());
        staff.setStatus(Staff.StaffStatus.active);
        
        if (staff.getPasswordHash() != null && !staff.getPasswordHash().isEmpty()) {
            staff.setPasswordHash(passwordEncoder.encode(staff.getPasswordHash()));
        } else {
            staff.setPasswordHash(passwordEncoder.encode("password123"));
        }
        
        Staff saved = staffRepository.save(staff);
        saved.setPasswordHash(null);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", saved,
            "message", "Staff created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable Long id, @RequestBody Staff staff) {
        Optional<Staff> existingOpt = staffRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Staff existing = existingOpt.get();
        
        existing.setFirstName(staff.getFirstName());
        existing.setLastName(staff.getLastName());
        existing.setEmail(staff.getEmail());
        existing.setPhone(staff.getPhone());
        existing.setMobile(staff.getMobile());
        existing.setPosition(staff.getPosition());
        existing.setDepartment(staff.getDepartment());
        existing.setEmployeeNumber(staff.getEmployeeNumber());
        existing.setIdNumber(staff.getIdNumber());
        existing.setPassportNumber(staff.getPassportNumber());
        existing.setNationality(staff.getNationality());
        existing.setDateOfBirth(staff.getDateOfBirth());
        existing.setHireDate(staff.getHireDate());
        existing.setTerminationDate(staff.getTerminationDate());
        existing.setStatus(staff.getStatus());
        existing.setRole(staff.getRole());
        existing.setSalary(staff.getSalary());
        existing.setHourlyRate(staff.getHourlyRate());
        existing.setBankName(staff.getBankName());
        existing.setBankAccount(staff.getBankAccount());
        existing.setBankBranchCode(staff.getBankBranchCode());
        existing.setEmergencyContact(staff.getEmergencyContact());
        existing.setEmergencyPhone(staff.getEmergencyPhone());
        existing.setNotes(staff.getNotes());
        existing.setProfileImageUrl(staff.getProfileImageUrl());
        existing.setCanResetPasswords(staff.getCanResetPasswords());
        existing.setCanApprove(staff.getCanApprove());
        existing.setAddress(staff.getAddress());
        existing.setAge(staff.getAge());
        existing.setDob(staff.getDob());
        existing.setUpdatedAt(new Date());
        
        if (staff.getPasswordHash() != null && !staff.getPasswordHash().isEmpty()) {
            existing.setPasswordHash(passwordEncoder.encode(staff.getPasswordHash()));
        }
        
        Staff updated = staffRepository.save(existing);
        updated.setPasswordHash(null);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", updated,
            "message", "Staff updated successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        Optional<Staff> staffOpt = staffRepository.findById(id);
        if (staffOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Staff staff = staffOpt.get();
        staff.setDeletedAt(new Date());
        staffRepository.save(staff);
        
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Staff deleted successfully"
        ));
    }
}