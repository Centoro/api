package com.centoro.api.service;

import com.centoro.api.entities.Contract;
import com.centoro.api.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {
    
    @Autowired
    private ContractRepository contractRepository;
    
    @Transactional
    public Contract createContract(Contract contract) {
        contract.setContractId("CTR-" + String.format("%04d", System.currentTimeMillis() % 10000));
        contract.setCreatedAt(new Date());
        contract.setUpdatedAt(new Date());
        return contractRepository.save(contract);
    }
    
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }
    
    public List<Contract> getContractsByCategory(String category) {
        return contractRepository.findByCategory(category);
    }
    
    public Optional<Contract> getContractById(Long id) {
        return contractRepository.findById(id);
    }
    
    public Optional<Contract> getContractByContractId(String contractId) {
        return contractRepository.findByContractId(contractId);
    }
    
    @Transactional
    public Contract updateContract(Long id, Contract contract) {
        Contract existing = contractRepository.findById(id).orElseThrow();
        existing.setName(contract.getName());
        existing.setCategory(contract.getCategory());
        existing.setFileUrl(contract.getFileUrl());
        existing.setIssuedTo(contract.getIssuedTo());
        existing.setUploadedAt(contract.getUploadedAt());
        existing.setUpdatedAt(new Date());
        return contractRepository.save(existing);
    }
    
    @Transactional
    public void deleteContract(Long id) {
        contractRepository.deleteById(id);
    }
}