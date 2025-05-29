package com.example.tubes.services;

import com.example.tubes.exception.NotFoundException;
import com.example.tubes.exception.ResourceNotFoundException;
import com.example.tubes.model.Transaction;
import com.example.tubes.model.Wallet;
import com.example.tubes.model.User;
import com.example.tubes.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserService userService;

    public List<Wallet> getAllWallets() {
        User currentUser = userService.getCurrentUser();
        return walletRepository.findByUser(currentUser);
    }

    public Wallet getWalletById(Long id) {
        User currentUser = userService.getCurrentUser();
        Optional<Wallet> walletOptional = walletRepository.findByIdAndUserId(id, currentUser.getId());
        
        if (walletOptional.isEmpty()) {
            throw new NotFoundException("Wallet not found with id: " + id);
        }

        Wallet wallet = walletOptional.get();

        return wallet;
    }

    public Wallet createWallet(Wallet wallet) {
        User currentUser = userService.getCurrentUser();
        if (walletRepository.existsByNameAndUserId(wallet.getName(), currentUser.getId())) {
            throw new RuntimeException("Wallet with this name already exists");
        }

        wallet.setUser(currentUser);
        return walletRepository.save(wallet);
    }

    public Wallet updateWallet(Long id, Wallet walletData) {
        User currentUser = userService.getCurrentUser();
        return walletRepository.findByIdAndUserId(id, currentUser.getId())
                .map(wallet -> {
                    wallet.setName(walletData.getName());
                    wallet.setBalance(walletData.getBalance());
                    return walletRepository.save(wallet);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with id: " + id));
    }

    public void deleteWallet(Long id) {
        User currentUser = userService.getCurrentUser();
        Wallet wallet = walletRepository.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with id: " + id));
        walletRepository.delete(wallet);
    }

    public Wallet addTransaction(Long walletId, Transaction transaction) {
        Wallet wallet = getWalletById(walletId);
        wallet.addTransaction(transaction);
        return walletRepository.save(wallet);
    }

    public void removeTransaction(Long walletId, Long transactionId) {
        Wallet wallet = getWalletById(walletId);
        wallet.getTransactions().stream()
                .filter(t -> t.getId().equals(transactionId))
                .findFirst()
                .ifPresent(wallet::removeTransaction);
        walletRepository.save(wallet);
    }

    public double calculateWalletBalance(Long walletId) {
        Wallet wallet = getWalletById(walletId);
        wallet.calculateBalance();
        return walletRepository.save(wallet).getBalance();
    }
    
    public double getTotalExpense(Long walletId) {
        Wallet wallet = getWalletById(walletId);
        return wallet.getTotalExpense();
    }
    
    public double getTotalIncome(Long walletId) {
        Wallet wallet = getWalletById(walletId);
        return wallet.getTotalIncome();
    }
}
