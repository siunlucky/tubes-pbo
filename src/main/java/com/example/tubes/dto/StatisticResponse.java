package com.example.tubes.dto;

import java.util.List;

public class DashboardResponse {
    private Long walletId;
    private Double totalTransaction;
    private Double totalIncome;
    private Double totalOutcome;
    
    private List<TransactionByTypeDTO> allTransactionByType;
    private List<TransactionByCategoryDTO> allTransactionByCategory;

    public List<TransactionByTypeDTO> getAllTransactionByType() {
        return allTransactionByType;
    }

    public void setAllTransactionByType(List<TransactionByTypeDTO> allTransactionByType) {
        this.allTransactionByType = allTransactionByType;
    }

    public List<TransactionByCategoryDTO> getAllTransactionByCategory() {
        return allTransactionByCategory;
    }

    public void setAllTransactionByCategory(List<TransactionByCategoryDTO> allTransactionByCategory) {
        this.allTransactionByCategory = allTransactionByCategory;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Double getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(Double totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalOutcome() {
        return totalOutcome;
    }

    public void setTotalOutcome(Double totalOutcome) {
        this.totalOutcome = totalOutcome;   
    }
}
