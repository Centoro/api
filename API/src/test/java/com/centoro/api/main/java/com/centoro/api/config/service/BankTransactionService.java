package com.centoro.api.service;

import com.centoro.api.entities.BankTransaction;
import com.centoro.api.repositories.BankTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BankTransactionService {
    
    @Autowired
    private BankTransactionRepository bankTransactionRepository;
    
    @Transactional
    public BankTransaction createTransaction(BankTransaction transaction) {
        transaction.setBankId("BNK-" + String.format("%04d", System.currentTimeMillis() % 10000));
        transaction.setCreatedAt(new Date());
        if (transaction.getReconciled() == null) transaction.setReconciled(false);
        return bankTransactionRepository.save(transaction);
    }
    
    public List<BankTransaction> getAllTransactions() {
        return bankTransactionRepository.findAll();
    }
    
    public List<BankTransaction> getUnreconciledTransactions() {
        return bankTransactionRepository.findByReconciled(false);
    }
    
    public List<BankTransaction> getTransactionsByDateRange(Date start, Date end) {
        return bankTransactionRepository.findByDateBetween(start, end);
    }
    
    public Optional<BankTransaction> getTransactionById(Long id) {
        return bankTransactionRepository.findById(id);
    }
    
    @Transactional
    public BankTransaction reconcileTransaction(Long id) {
        BankTransaction transaction = bankTransactionRepository.findById(id).orElseThrow();
        transaction.setReconciled(true);
        return bankTransactionRepository.save(transaction);
    }
    
    @Transactional
    public void deleteTransaction(Long id) {
        bankTransactionRepository.deleteById(id);
    }
}