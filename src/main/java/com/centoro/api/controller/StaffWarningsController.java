package com.centoro.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.centoro.api.entities.StaffWarning;
import com.centoro.api.repositories.StaffWarningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@RestController
@RequestMapping("/api/staff/warnings")
@CrossOrigin(origins = "*")
public class StaffWarningsController {
    
    @Autowired
    private StaffWarningRepository warningRepository;
    
    @GetMapping
    public ResponseEntity<?> getAllWarnings() {
        return ResponseEntity.ok(Map.of("success", true, "data", warningRepository.findAll()));
    }
    
    // ✅ FIX: Changed @PathVariable from Long to String
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<?> getWarningsByStaff(@PathVariable String staffId) {
        return ResponseEntity.ok(Map.of("success", true, "data", warningRepository.findByStaffId(staffId)));
    }
    
    @PostMapping
    public ResponseEntity<?> createWarning(@RequestBody StaffWarning warning) {
        warning.setWarningId("WRN-" + System.currentTimeMillis());
        warning.setCreatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", warningRepository.save(warning)));
    }
}
