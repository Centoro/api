package com.centoro.api.controller;

import com.centoro.api.entities.PayrollRecord;
import com.centoro.api.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "*")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;
    
    @GetMapping("/period/{period}")
    public ResponseEntity<?> getPayrollForPeriod(@PathVariable String period) {
        List<Map<String, Object>> payroll = payrollService.getPayrollForPeriod(period);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", payroll
        ));
    }
    
    @GetMapping("/period/{period}/summary")
    public ResponseEntity<?> getPayrollSummary(@PathVariable String period) {
        Map<String, Object> summary = payrollService.getPayrollSummary(period);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", summary
        ));
    }
    
    // FIX: Changed @PathVariable to String
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<?> getStaffPayrollHistory(@PathVariable String staffId) {
        List<PayrollRecord> records = payrollService.getStaffPayrollHistory(staffId);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", records
        ));
    }
    
    // FIX: Changed @PathVariable to String
    @GetMapping("/staff/{staffId}/calculate")
    public ResponseEntity<?> calculateStaffPayroll(@PathVariable String staffId) {
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Use /api/staff/" + staffId + " to get staff data"
        ));
    }
    
    @PostMapping("/period/{period}/process")
    public ResponseEntity<?> processPayroll(@PathVariable String period) {
        try {
            List<PayrollRecord> records = payrollService.processPayroll(period);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", records,
                "message", "Payroll processed successfully for " + period
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }
    
    @PostMapping("/records/{recordId}/pay")
    public ResponseEntity<?> markRecordAsPaid(@PathVariable Long recordId) {
        try {
            PayrollRecord record = payrollService.markAsPaid(recordId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", record,
                "message", "Payroll record marked as paid"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
