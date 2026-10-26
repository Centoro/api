package com.centoro.api.controller;

import com.centoro.api.dto.TaxPeriodDTO;
import com.centoro.api.entities.TaxPeriod;
import com.centoro.api.service.TaxPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tax-periods")
@CrossOrigin(origins = "*")
public class TaxPeriodController {

    @Autowired
    private TaxPeriodService taxPeriodService;

    @GetMapping
    public ResponseEntity<?> getAllTaxPeriods() {
        List<TaxPeriod> taxPeriods = taxPeriodService.getAllTaxPeriods();
        List<TaxPeriodDTO> taxPeriodDTOs = taxPeriods.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", taxPeriodDTOs
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaxPeriod(@PathVariable Long id) {
        Optional<TaxPeriod> taxPeriodOpt = taxPeriodService.getTaxPeriodById(id);
        if (taxPeriodOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(taxPeriodOpt.get())
        ));
    }

    @GetMapping("/period/{period}")
    public ResponseEntity<?> getTaxPeriodByPeriod(@PathVariable String period) {
        Optional<TaxPeriod> taxPeriodOpt = taxPeriodService.getTaxPeriodByPeriod(period);
        if (taxPeriodOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(taxPeriodOpt.get())
        ));
    }

    @PostMapping
    public ResponseEntity<?> createTaxPeriod(@RequestBody TaxPeriod taxPeriod) {
        TaxPeriod saved = taxPeriodService.createTaxPeriod(taxPeriod);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Tax period created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaxPeriod(@PathVariable Long id, @RequestBody TaxPeriod taxPeriod) {
        try {
            TaxPeriod updated = taxPeriodService.updateTaxPeriod(id, taxPeriod);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Tax period updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaxPeriod(@PathVariable Long id) {
        if (!taxPeriodService.getTaxPeriodById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        taxPeriodService.deleteTaxPeriod(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Tax period deleted successfully"
        ));
    }

    private TaxPeriodDTO convertToDTO(TaxPeriod taxPeriod) {
        TaxPeriodDTO dto = new TaxPeriodDTO();
        dto.setId(taxPeriod.getId());
        dto.setPeriod(taxPeriod.getPeriod());
        dto.setOutputVat(taxPeriod.getOutputVat());
        dto.setInputVat(taxPeriod.getInputVat());
        dto.setPayable(taxPeriod.getPayable());
        dto.setStatus(taxPeriod.getStatus());
        dto.setCreatedAt(taxPeriod.getCreatedAt());
        dto.setUpdatedAt(taxPeriod.getUpdatedAt());
        return dto;
    }
}