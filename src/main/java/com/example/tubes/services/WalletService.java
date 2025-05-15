package com.example.tubes.services;

import com.example.tubes.model.Transaction;
import com.example.tubes.model.Wallet;
import com.example.tubes.model.User;
import com.example.tubes.repository.WalletRepository;
import com.example.tubes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Wallet> getUserWallets(int userId) {
        return walletRepository.findByUserId(userId);
    }
    
    public Wallet getWalletById(int walletId, int userId) {
        return walletRepository.findByIdAndUserId(walletId, userId);
    }
    
    @Transactional
    public Wallet createWallet(Wallet wallet, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        if (walletRepository.existsByNameAndUserId(wallet.getName(), userId)) {
            throw new RuntimeException("Wallet with this name already exists");
        }
        
        wallet.setUser(userOptional.get());
        wallet.setBalance(0.0);
        return walletRepository.save(wallet);
    }
    
    @Transactional
    public Wallet updateWallet(int walletId, Wallet walletDetails, int userId) {
        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId);
        if (wallet == null) {
            throw new RuntimeException("Wallet not found or doesn't belong to user");
        }
        
        wallet.setName(walletDetails.getName());
        return walletRepository.save(wallet);
    }
    
    @Transactional
    public void deleteWallet(int walletId, int userId) {
        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId);
        if (wallet == null) {
            throw new RuntimeException("Wallet not found or doesn't belong to user");
        }
        
        walletRepository.delete(wallet);
    }
    
    @Transactional
    public void addTransaction(Transaction transaction, int walletId, int userId) {
        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId);
        if (wallet == null) {
            throw new RuntimeException("Wallet not found or doesn't belong to user");
        }
        
        wallet.addTransaction(transaction);
        walletRepository.save(wallet);
    }
    
    public void showStatistics(int walletId, int userId) {
        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId);
        if (wallet == null) {
            throw new RuntimeException("Wallet not found or doesn't belong to user");
        }
        
        wallet.showStatistics();
    }
    
    public void calculateBalance(int walletId, int userId) {
        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId);
        if (wallet == null) {
            throw new RuntimeException("Wallet not found or doesn't belong to user");
        }
        
        wallet.calculateBalance();
        walletRepository.save(wallet);
    }
    
    public double getTotalExpense(int walletId, int userId) {
        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId);
        if (wallet == null) {
            throw new RuntimeException("Wallet not found or doesn't belong to user");
        }
        
        return wallet.getTotalExpense();
    }
    
    public double getTotalIncome(int walletId, int userId) {
        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId);
        if (wallet == null) {
            throw new RuntimeException("Wallet not found or doesn't belong to user");
        }
        
        return wallet.getTotalIncome();
    }
    
    public void exportToCSV(int walletId, int userId) {
        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId);
        if (wallet == null) {
            throw new RuntimeException("Wallet not found or doesn't belong to user");
        }
        
        wallet.exportToCSV();
    }
    
    public Map<String, Object> getWalletSummary(int walletId, int userId) {
        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId);
        if (wallet == null) {
            throw new RuntimeException("Wallet not found or doesn't belong to user");
        }
        
        return Map.of(
            "walletId", wallet.getId(),
            "walletName", wallet.getName(),
            "balance", wallet.getBalance(),
            "totalIncome", wallet.getTotalIncome(),
            "totalExpense", wallet.getTotalExpense(),
            "transactionCount", wallet.getTransactions().size()
        );
    }
}
