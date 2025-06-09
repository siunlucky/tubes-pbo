package com.example.tubes.dto;

public class TransactionByTypeDTO {
    private String type;
    private Double amount;

    public TransactionByTypeDTO(String type, Double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
