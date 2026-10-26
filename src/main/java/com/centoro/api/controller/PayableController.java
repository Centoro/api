package com.centoro.api.controller;

import com.centoro.api.dto.PayableDTO;
import com.centoro.api.entities.Payable;
import com.centoro.api.service.PayableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payables")
@CrossOrigin(origins = "*")
public class PayableController {

    @Autowired
    private PayableService payableService;

    @GetMapping
    public ResponseEntity<?> getAllPayables() {
        List<Payable> payables = payableService.getAllPayables();
        List<PayableDTO> payableDTOs = payables.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", payableDTOs
        ));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getPayablesByStatus(@PathVariable String status) {
        List<Payable> payables = payableService.getPayablesByStatus(status);
        List<PayableDTO> payableDTOs = payables.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", payableDTOs
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayable(@PathVariable Long id) {
        Optional<Payable> payableOpt = payableService.getPayableById(id);
        if (payableOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(payableOpt.get())
        ));
    }

    @PostMapping
    public ResponseEntity<?> createPayable(@RequestBody Payable payable) {
        Payable saved = payableService.createPayable(payable);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Payable created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePayable(@PathVariable Long id, @RequestBody Payable payable) {
        try {
            Payable updated = payableService.updatePayable(id, payable);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Payable updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> markAsPaid(@PathVariable Long id) {
        try {
            Payable paid = payableService.markAsPaid(id, new Date());
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(paid),
                "message", "Payable marked as paid"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayable(@PathVariable Long id) {
        if (!payableService.getPayableById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        payableService.deletePayable(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Payable deleted successfully"
        ));
    }

    private PayableDTO convertToDTO(Payable payable) {
        PayableDTO dto = new PayableDTO();
        dto.setId(payable.getId());
        dto.setPayableId(payable.getPayableId());
        dto.setSupplier(payable.getSupplier());
        dto.setDescription(payable.getDescription());
        dto.setAmount(payable.getAmount());
        dto.setVat(payable.getVat());
        dto.setDueDate(payable.getDueDate());
        dto.setStatus(payable.getStatus());
        dto.setPaidDate(payable.getPaidDate());
        dto.setCreatedAt(payable.getCreatedAt());
        dto.setUpdatedAt(payable.getUpdatedAt());
        return dto;
    }
}