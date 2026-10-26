package com.centoro.api.service;

import com.centoro.api.entities.PayrollRecord;
import com.centoro.api.entities.Staff;
import com.centoro.api.repositories.PayrollRecordRepository;
import com.centoro.api.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PayrollService {
    
    @Autowired
    private PayrollRecordRepository payrollRecordRepository;
    
    @Autowired
    private StaffRepository staffRepository;
    
    private static final double UIF_RATE = 0.01;
    private static final double MAX_UIF_EARNINGS = 17712.0;
    
    public double calculatePAYE(double gross) {
        if (gross <= 23700) return 0;
        if (gross <= 37000) return (gross - 23700) * 0.18;
        if (gross <= 51200) return 2394 + (gross - 37000) * 0.26;
        if (gross <= 67300) return 6066 + (gross - 51200) * 0.31;
        if (gross <= 87400) return 11057 + (gross - 67300) * 0.36;
        if (gross <= 181900) return 18293 + (gross - 87400) * 0.39;
        return 55067 + (gross - 181900) * 0.41;
    }
    
    public double calculateUIF(double gross) {
        double taxableEarnings = Math.min(gross, MAX_UIF_EARNINGS);
        return taxableEarnings * UIF_RATE;
    }
    
    public double calculateNet(double gross) {
        return gross - calculatePAYE(gross) - calculateUIF(gross);
    }
    
    public Map<String, Object> calculatePayroll(Staff staff) {
        double gross = staff.getSalary() != null ? staff.getSalary() : 0.0;
        double paye = calculatePAYE(gross);
        double uif = calculateUIF(gross);
        double net = gross - paye - uif;
        
        Map<String, Object> result = new HashMap<>();
        result.put("staffId", staff.getId());
        result.put("name", staff.getFirstName() + " " + staff.getLastName());
        result.put("role", staff.getRole() != null ? staff.getRole().name() : "");
        result.put("gross", gross);
        result.put("paye", paye);
        result.put("uif", uif);
        result.put("net", net);
        return result;
    }
    
    public List<Map<String, Object>> getPayrollForPeriod(String period) {
        List<Staff> activeStaff = staffRepository.findByStatus(Staff.StaffStatus.active);
        List<Map<String, Object>> results = new ArrayList<>();
        
        for (Staff staff : activeStaff) {
            Map<String, Object> calc = calculatePayroll(staff);
            
            List<PayrollRecord> existing = payrollRecordRepository.findByPeriod(period);
            Optional<PayrollRecord> record = existing.stream()
                .filter(r -> r.getStaffId().equals(staff.getId()))
                .findFirst();
            
            calc.put("status", record.isPresent() ? record.get().getStatus() : "pending");
            calc.put("recordId", record.isPresent() ? record.get().getRecordId() : null);
            results.add(calc);
        }
        
        return results;
    }
    
    @Transactional
    public List<PayrollRecord> processPayroll(String period) {
        List<Staff> activeStaff = staffRepository.findByStatus(Staff.StaffStatus.active);
        List<PayrollRecord> records = new ArrayList<>();
        
        List<PayrollRecord> existing = payrollRecordRepository.findPendingByPeriod(period);
        payrollRecordRepository.deleteAll(existing);
        
        for (Staff staff : activeStaff) {
            double gross = staff.getSalary() != null ? staff.getSalary() : 0.0;
            double paye = calculatePAYE(gross);
            double uif = calculateUIF(gross);
            double net = gross - paye - uif;
            
            PayrollRecord record = new PayrollRecord();
            record.setRecordId("PR-" + String.format("%04d", System.currentTimeMillis() % 10000));
            record.setStaffId(staff.getId());
            record.setPeriod(period);
            record.setGross(gross);
            record.setPaye(paye);
            record.setUif(uif);
            record.setNet(net);
            record.setStatus("pending");
            record.setCreatedAt(new Date());
            record.setUpdatedAt(new Date());
            
            records.add(payrollRecordRepository.save(record));
        }
        
        return records;
    }
    
    @Transactional
    public PayrollRecord markAsPaid(Long recordId) {
        PayrollRecord record = payrollRecordRepository.findById(recordId)
            .orElseThrow(() -> new RuntimeException("Payroll record not found"));
        
        record.setStatus("paid");
        record.setPaidAt(new Date());
        record.setUpdatedAt(new Date());
        
        return payrollRecordRepository.save(record);
    }
    
    public Map<String, Object> getPayrollSummary(String period) {
        List<PayrollRecord> records = payrollRecordRepository.findByPeriod(period);
        
        double totalGross = 0.0;
        double totalPaye = 0.0;
        double totalUif = 0.0;
        double totalNet = 0.0;
        long paidCount = 0;
        long pendingCount = 0;
        
        for (PayrollRecord record : records) {
            totalGross += record.getGross() != null ? record.getGross() : 0.0;
            totalPaye += record.getPaye() != null ? record.getPaye() : 0.0;
            totalUif += record.getUif() != null ? record.getUif() : 0.0;
            totalNet += record.getNet() != null ? record.getNet() : 0.0;
            
            if ("paid".equals(record.getStatus())) {
                paidCount++;
            } else {
                pendingCount++;
            }
        }
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalGross", totalGross);
        summary.put("totalPaye", totalPaye);
        summary.put("totalUif", totalUif);
        summary.put("totalNet", totalNet);
        summary.put("paidCount", paidCount);
        summary.put("pendingCount", pendingCount);
        summary.put("totalEmployees", records.size());
        
        return summary;
    }
    
    public List<PayrollRecord> getStaffPayrollHistory(Long staffId) {
        return payrollRecordRepository.findByStaffId(staffId);
    }
}