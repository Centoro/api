package com.centoro.api.service;

import com.centoro.api.entities.TaxPeriod;
import com.centoro.api.repositories.TaxPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaxPeriodService {
    
    @Autowired
    private TaxPeriodRepository taxPeriodRepository;
    
    @Transactional
    public TaxPeriod createTaxPeriod(TaxPeriod taxPeriod) {
        taxPeriod.setCreatedAt(new Date());
        taxPeriod.setUpdatedAt(new Date());
        if (taxPeriod.getStatus() == null) taxPeriod.setStatus("pending");
        if (taxPeriod.getOutputVat() == null) taxPeriod.setOutputVat(0.0);
        if (taxPeriod.getInputVat() == null) taxPeriod.setInputVat(0.0);
        if (taxPeriod.getPayable() == null) taxPeriod.setPayable(0.0);
        return taxPeriodRepository.save(taxPeriod);
    }
    
    public List<TaxPeriod> getAllTaxPeriods() {
        return taxPeriodRepository.findAll();
    }
    
    public Optional<TaxPeriod> getTaxPeriodById(Long id) {
        return taxPeriodRepository.findById(id);
    }
    
    public Optional<TaxPeriod> getTaxPeriodByPeriod(String period) {
        return taxPeriodRepository.findByPeriod(period);
    }
    
    @Transactional
    public TaxPeriod updateTaxPeriod(Long id, TaxPeriod taxPeriod) {
        TaxPeriod existing = taxPeriodRepository.findById(id).orElseThrow();
        existing.setPeriod(taxPeriod.getPeriod());
        existing.setOutputVat(taxPeriod.getOutputVat());
        existing.setInputVat(taxPeriod.getInputVat());
        existing.setPayable(taxPeriod.getPayable());
        existing.setStatus(taxPeriod.getStatus());
        existing.setUpdatedAt(new Date());
        return taxPeriodRepository.save(existing);
    }
    
    @Transactional
    public void deleteTaxPeriod(Long id) {
        taxPeriodRepository.deleteById(id);
    }
}