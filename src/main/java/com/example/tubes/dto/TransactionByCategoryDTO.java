package com.example.tubes.dto;

public class TransactionByCategoryDTO {
    private String category;
    private Integer amount;

    public TransactionByCategoryDTO(String category, Integer amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public Integer getAmount() {
        return amount;
    }
}
