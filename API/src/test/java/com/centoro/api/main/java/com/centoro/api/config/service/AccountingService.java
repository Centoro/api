package com.centoro.api.service;

import com.centoro.api.entities.JournalEntry;
import com.centoro.api.entities.JournalLine;
import com.centoro.api.repositories.JournalEntryRepository;
import com.centoro.api.repositories.JournalLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class AccountingService {
    
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    
    @Autowired
    private JournalLineRepository journalLineRepository;
    
    @Transactional
    public JournalEntry createJournalEntry(JournalEntry entry) {
        entry.setJournalId("JRN-" + String.format("%04d", System.currentTimeMillis() % 10000));
        entry.setCreatedAt(new Date());
        JournalEntry saved = journalEntryRepository.save(entry);
        
        // Save lines
        if (entry.getLines() != null) {
            for (JournalLine line : entry.getLines()) {
                line.setJournalEntryId(saved.getId());
                journalLineRepository.save(line);
            }
        }
        
        return saved;
    }
    
    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepository.findAll();
    }
    
    public Optional<JournalEntry> getJournalEntryById(Long id) {
        return journalEntryRepository.findById(id);
    }
    
    public List<JournalLine> getJournalLines(Long entryId) {
        return journalLineRepository.findByJournalEntryId(entryId);
    }
    
    @Transactional
    public JournalEntry updateJournalEntry(Long id, JournalEntry entry) {
        JournalEntry existing = journalEntryRepository.findById(id).orElseThrow();
        existing.setDate(entry.getDate());
        existing.setDescription(entry.getDescription());
        existing.setReference(entry.getReference());
        
        // Delete old lines and add new ones
        journalLineRepository.deleteAll(journalLineRepository.findByJournalEntryId(id));
        
        if (entry.getLines() != null) {
            for (JournalLine line : entry.getLines()) {
                line.setJournalEntryId(id);
                journalLineRepository.save(line);
            }
        }
        
        return journalEntryRepository.save(existing);
    }
    
    @Transactional
    public void deleteJournalEntry(Long id) {
        journalLineRepository.deleteAll(journalLineRepository.findByJournalEntryId(id));
        journalEntryRepository.deleteById(id);
    }
    
    // ============ TRIAL BALANCE ============
    
    public Map<String, Double> getTrialBalance() {
        Map<String, Double> balances = new HashMap<>();
        List<JournalEntry> entries = journalEntryRepository.findAll();
        
        for (JournalEntry entry : entries) {
            for (JournalLine line : journalLineRepository.findByJournalEntryId(entry.getId())) {
                String account = line.getAccount();
                double debit = line.getDebit() != null ? line.getDebit() : 0.0;
                double credit = line.getCredit() != null ? line.getCredit() : 0.0;
                
                // Debit increases asset and expense accounts, decreases liability, equity, income
                // We store balance as positive for debit balance, negative for credit balance
                double balance = debit - credit;
                balances.merge(account, balance, Double::sum);
            }
        }
        
        return balances;
    }
    
    // ============ FINANCIAL STATEMENTS ============
    
    public Map<String, Object> getProfitAndLoss() {
        Map<String, Double> balances = getTrialBalance();
        Map<String, Object> result = new HashMap<>();
        
        double incomeTotal = 0.0;
        double expenseTotal = 0.0;
        Map<String, Double> income = new HashMap<>();
        Map<String, Double> expenses = new HashMap<>();
        
        for (Map.Entry<String, Double> entry : balances.entrySet()) {
            String account = entry.getKey();
            double balance = entry.getValue();
            
            // Income accounts have credit balance (negative in our convention)
            if (accountType(account).equals("income") && balance < 0) {
                double amt = Math.abs(balance);
                income.put(account, amt);
                incomeTotal += amt;
            }
            // Expense accounts have debit balance (positive in our convention)
            if (accountType(account).equals("expense") && balance > 0) {
                expenses.put(account, balance);
                expenseTotal += balance;
            }
        }
        
        result.put("income", income);
        result.put("incomeTotal", incomeTotal);
        result.put("expenses", expenses);
        result.put("expenseTotal", expenseTotal);
        result.put("netProfit", incomeTotal - expenseTotal);
        
        return result;
    }
    
    public Map<String, Object> getBalanceSheet() {
        Map<String, Double> balances = getTrialBalance();
        Map<String, Object> result = new HashMap<>();
        
        Map<String, Double> assets = new HashMap<>();
        Map<String, Double> liabilities = new HashMap<>();
        Map<String, Double> equity = new HashMap<>();
        
        double assetTotal = 0.0;
        double liabilityTotal = 0.0;
        double equityTotal = 0.0;
        
        for (Map.Entry<String, Double> entry : balances.entrySet()) {
            String account = entry.getKey();
            double balance = entry.getValue();
            String type = accountType(account);
            
            if (type.equals("asset") && balance > 0) {
                assets.put(account, balance);
                assetTotal += balance;
            } else if (type.equals("liability") && balance < 0) {
                double amt = Math.abs(balance);
                liabilities.put(account, amt);
                liabilityTotal += amt;
            } else if (type.equals("equity") && balance < 0) {
                double amt = Math.abs(balance);
                equity.put(account, amt);
                equityTotal += amt;
            }
        }
        
        // Add net income to equity
        Map<String, Object> pl = getProfitAndLoss();
        double netProfit = (double) pl.get("netProfit");
        if (netProfit > 0) {
            equity.put("Net Income (current period)", netProfit);
            equityTotal += netProfit;
        }
        
        result.put("assets", assets);
        result.put("assetTotal", assetTotal);
        result.put("liabilities", liabilities);
        result.put("liabilityTotal", liabilityTotal);
        result.put("equity", equity);
        result.put("equityTotal", equityTotal);
        result.put("liabilitiesEquityTotal", liabilityTotal + equityTotal);
        
        return result;
    }
    
    public Map<String, Object> getCashFlow() {
        Map<String, Double> balances = getTrialBalance();
        Map<String, Object> result = new HashMap<>();
        
        // Find cash/bank accounts
        Map<String, Double> cashAccounts = new HashMap<>();
        double netMovement = 0.0;
        
        for (Map.Entry<String, Double> entry : balances.entrySet()) {
            String account = entry.getKey().toLowerCase();
            double balance = entry.getValue();
            
            if (account.contains("bank") || account.contains("cash") || account.contains("fnb")) {
                cashAccounts.put(entry.getKey(), balance);
                netMovement += balance;
            }
        }
        
        result.put("cashAccounts", cashAccounts);
        result.put("netMovement", netMovement);
        result.put("message", "For full operating/investing/financing classification, accounts must be mapped to cash-flow categories.");
        
        return result;
    }
    
    private String accountType(String accountName) {
        String name = accountName.toLowerCase();
        if (name.contains("income") || name.contains("sales") || name.contains("revenue")) return "income";
        if (name.contains("expense") || name.contains("cost") || name.contains("salary") || 
            name.contains("wage") || name.contains("rent") || name.contains("software") || 
            name.contains("marketing") || name.contains("utilities") || name.contains("bank charges") || 
            name.contains("general")) return "expense";
        if (name.contains("creditor") || name.contains("loan") || name.contains("vat payable") || 
            name.contains("liability")) return "liability";
        if (name.contains("equity") || name.contains("capital") || name.contains("retained") || 
            name.contains("owner")) return "equity";
        return "asset";
    }
}