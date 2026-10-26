package com.centoro.api.controller;

import com.centoro.api.dto.ContractDTO;
import com.centoro.api.entities.Contract;
import com.centoro.api.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contracts")
@CrossOrigin(origins = "*")
public class ContractsController {

    @Autowired
    private ContractService contractService;

    @GetMapping
    public ResponseEntity<?> getAllContracts() {
        List<Contract> contracts = contractService.getAllContracts();
        List<ContractDTO> contractDTOs = contracts.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", contractDTOs
        ));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getContractsByCategory(@PathVariable String category) {
        List<Contract> contracts = contractService.getContractsByCategory(category);
        List<ContractDTO> contractDTOs = contracts.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", contractDTOs
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContract(@PathVariable Long id) {
        Optional<Contract> contractOpt = contractService.getContractById(id);
        if (contractOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(contractOpt.get())
        ));
    }

    @PostMapping
    public ResponseEntity<?> createContract(@RequestBody Contract contract) {
        Contract saved = contractService.createContract(contract);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "data", convertToDTO(saved),
            "message", "Contract created successfully"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContract(@PathVariable Long id, @RequestBody Contract contract) {
        try {
            Contract updated = contractService.updateContract(id, contract);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", convertToDTO(updated),
                "message", "Contract updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContract(@PathVariable Long id) {
        if (!contractService.getContractById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        contractService.deleteContract(id);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Contract deleted successfully"
        ));
    }

    private ContractDTO convertToDTO(Contract contract) {
        ContractDTO dto = new ContractDTO();
        dto.setId(contract.getId());
        dto.setContractId(contract.getContractId());
        dto.setName(contract.getName());
        dto.setCategory(contract.getCategory());
        dto.setFileUrl(contract.getFileUrl());
        dto.setIssuedTo(contract.getIssuedTo());
        dto.setUploadedAt(contract.getUploadedAt());
        dto.setCreatedAt(contract.getCreatedAt());
        dto.setUpdatedAt(contract.getUpdatedAt());
        return dto;
    }
}