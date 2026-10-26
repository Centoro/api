package com.centoro.api.dto;

public class DepreciationDTO {
    private Double cost;
    private Double salvage;
    private Integer lifeYears;
    private Double depreciableAmount;
    private Double annualDepreciation;
    private Double monthlyDepreciation;
    private Integer monthsOwned;
    private Double accumulatedDepreciation;
    private Double bookValue;
    private Double depreciationPct;
    
    // Getters and Setters
    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }
    public Double getSalvage() { return salvage; }
    public void setSalvage(Double salvage) { this.salvage = salvage; }
    public Integer getLifeYears() { return lifeYears; }
    public void setLifeYears(Integer lifeYears) { this.lifeYears = lifeYears; }
    public Double getDepreciableAmount() { return depreciableAmount; }
    public void setDepreciableAmount(Double depreciableAmount) { this.depreciableAmount = depreciableAmount; }
    public Double getAnnualDepreciation() { return annualDepreciation; }
    public void setAnnualDepreciation(Double annualDepreciation) { this.annualDepreciation = annualDepreciation; }
    public Double getMonthlyDepreciation() { return monthlyDepreciation; }
    public void setMonthlyDepreciation(Double monthlyDepreciation) { this.monthlyDepreciation = monthlyDepreciation; }
    public Integer getMonthsOwned() { return monthsOwned; }
    public void setMonthsOwned(Integer monthsOwned) { this.monthsOwned = monthsOwned; }
    public Double getAccumulatedDepreciation() { return accumulatedDepreciation; }
    public void setAccumulatedDepreciation(Double accumulatedDepreciation) { this.accumulatedDepreciation = accumulatedDepreciation; }
    public Double getBookValue() { return bookValue; }
    public void setBookValue(Double bookValue) { this.bookValue = bookValue; }
    public Double getDepreciationPct() { return depreciationPct; }
    public void setDepreciationPct(Double depreciationPct) { this.depreciationPct = depreciationPct; }
}