package com.centoro.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.centoro.api.entities.StaffBonus;
import com.centoro.api.repositories.StaffBonusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@RestController
@RequestMapping("/api/staff/bonuses")
@CrossOrigin(origins = "*")
public class StaffBonusesController {
    
    @Autowired
    private StaffBonusRepository bonusRepository;
    
    @GetMapping
    public ResponseEntity<?> getAllBonuses() {
        return ResponseEntity.ok(Map.of("success", true, "data", bonusRepository.findAll()));
    }
    
    // ✅ FIX: @PathVariable MUST be String
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<?> getBonusesByStaff(@PathVariable String staffId) {
        return ResponseEntity.ok(Map.of("success", true, "data", bonusRepository.findByStaffId(staffId)));
    }
    
    @PostMapping
    public ResponseEntity<?> createBonus(@RequestBody StaffBonus bonus) {
        bonus.setBonusId("BNS-" + System.currentTimeMillis());
        bonus.setCreatedAt(new Date());
        return ResponseEntity.ok(Map.of("success", true, "data", bonusRepository.save(bonus)));
    }
}
