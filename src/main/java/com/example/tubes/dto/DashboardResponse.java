package com.example.tubes.dto;

import java.util.List;

public class DashboardResponse {
    private Long walletId;
    private Double totalBalance;
    
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

    public Double getTotalBalance() {
        return totalBalance;
    }
    
    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }
}
