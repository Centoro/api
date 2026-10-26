package com.centoro.api.dto;

public class InvoiceItemDTO {
    private Integer qty;
    private String description;
    private Double unitPrice;

    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
}