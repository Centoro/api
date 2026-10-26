package com.centoro.api.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "journal_lines")
public class JournalLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "journal_entry_id", nullable = false)
    private Long journalEntryId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_entry_id", insertable = false, updatable = false)
    private JournalEntry journalEntry;
    
    @Column(nullable = false)
    private String account;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double debit = 0.0;
    
    @Column(columnDefinition = "DECIMAL(18,2)")
    private Double credit = 0.0;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getJournalEntryId() { return journalEntryId; }
    public void setJournalEntryId(Long journalEntryId) { this.journalEntryId = journalEntryId; }
    public JournalEntry getJournalEntry() { return journalEntry; }
    public void setJournalEntry(JournalEntry journalEntry) { this.journalEntry = journalEntry; }
    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }
    public Double getDebit() { return debit; }
    public void setDebit(Double debit) { this.debit = debit; }
    public Double getCredit() { return credit; }
    public void setCredit(Double credit) { this.credit = credit; }
}