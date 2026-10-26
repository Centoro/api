package com.centoro.api.service;

import com.centoro.api.entities.Payable;
import com.centoro.api.repositories.PayableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PayableService {
    
    @Autowired
    private PayableRepository payableRepository;
    
    @Transactional
    public Payable createPayable(Payable payable) {
        payable.setPayableId("PAY-" + String.format("%04d", System.currentTimeMillis() % 10000));
        payable.setCreatedAt(new Date());
        payable.setUpdatedAt(new Date());
        if (payable.getStatus() == null) payable.setStatus("unpaid");
        if (payable.getVat() == null) payable.setVat(0.0);
        return payableRepository.save(payable);
    }
    
    public List<Payable> getAllPayables() {
        return payableRepository.findAll();
    }
    
    public List<Payable> getPayablesByStatus(String status) {
        return payableRepository.findByStatus(status);
    }
    
    public Optional<Payable> getPayableById(Long id) {
        return payableRepository.findById(id);
    }
    
    @Transactional
    public Payable updatePayable(Long id, Payable payable) {
        Payable existing = payableRepository.findById(id).orElseThrow();
        existing.setSupplier(payable.getSupplier());
        existing.setDescription(payable.getDescription());
        existing.setAmount(payable.getAmount());
        existing.setVat(payable.getVat());
        existing.setDueDate(payable.getDueDate());
        existing.setStatus(payable.getStatus());
        existing.setPaidDate(payable.getPaidDate());
        existing.setUpdatedAt(new Date());
        return payableRepository.save(existing);
    }
    
    @Transactional
    public Payable markAsPaid(Long id, Date paidDate) {
        Payable payable = payableRepository.findById(id).orElseThrow();
        payable.setStatus("paid");
        payable.setPaidDate(paidDate != null ? paidDate : new Date());
        payable.setUpdatedAt(new Date());
        return payableRepository.save(payable);
    }
    
    @Transactional
    public void deletePayable(Long id) {
        payableRepository.deleteById(id);
    }
}