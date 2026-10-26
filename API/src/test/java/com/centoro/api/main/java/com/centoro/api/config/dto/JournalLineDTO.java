package com.centoro.api.dto;

public class JournalLineDTO {
    private Long id;
    private Long journalEntryId;
    private String account;
    private Double debit;
    private Double credit;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getJournalEntryId() { return journalEntryId; }
    public void setJournalEntryId(Long journalEntryId) { this.journalEntryId = journalEntryId; }
    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }
    public Double getDebit() { return debit; }
    public void setDebit(Double debit) { this.debit = debit; }
    public Double getCredit() { return credit; }
    public void setCredit(Double credit) { this.credit = credit; }
}