package com.example.tubes.controller;

import com.example.tubes.model.Transaction;
import com.example.tubes.model.Wallet;
import com.example.tubes.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;
    
    @GetMapping
    public ResponseEntity<List<Wallet>> getUserWallets(@RequestAttribute("userId") int userId) {
        List<Wallet> wallets = walletService.getUserWallets(userId);
        return ResponseEntity.ok(wallets);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletById(
            @PathVariable("id") int walletId,
            @RequestAttribute("userId") int userId) {
        
        Wallet wallet = walletService.getWalletById(walletId, userId);
        if (wallet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(wallet);
    }
    
    @PostMapping
    public ResponseEntity<Wallet> createWallet(
            @RequestBody Wallet wallet,
            @RequestAttribute("userId") Long userId) {
        
        Wallet createdWallet = walletService.createWallet(wallet, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWallet);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Wallet> updateWallet(
            @PathVariable("id") int walletId,
            @RequestBody Wallet walletDetails,
            @RequestAttribute("userId") int userId) {
        
        try {
            Wallet updatedWallet = walletService.updateWallet(walletId, walletDetails, userId);
            return ResponseEntity.ok(updatedWallet);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWallet(
            @PathVariable("id") int walletId,
            @RequestAttribute("userId") int userId) {
        
        try {
            walletService.deleteWallet(walletId, userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{id}/transactions")
    public ResponseEntity<Void> addTransaction(
            @PathVariable("id") int walletId,
            @RequestBody Transaction transaction,
            @RequestAttribute("userId") int userId) {
        
        try {
            walletService.addTransaction(transaction, walletId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/{id}/statistics")
    public ResponseEntity<Map<String, Object>> getWalletStatistics(
            @PathVariable("id") int walletId,
            @RequestAttribute("userId") int userId) {
        
        try {
            Map<String, Object> statistics = walletService.getWalletSummary(walletId, userId);
            return ResponseEntity.ok(statistics);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{id}/calculate-balance")
    public ResponseEntity<Void> calculateBalance(
            @PathVariable("id") int walletId,
            @RequestAttribute("userId") int userId) {
        
        try {
            walletService.calculateBalance(walletId, userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}/total-expense")
    public ResponseEntity<Double> getTotalExpense(
            @PathVariable("id") int walletId,
            @RequestAttribute("userId") int userId) {
        
        try {
            double totalExpense = walletService.getTotalExpense(walletId, userId);
            return ResponseEntity.ok(totalExpense);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}/total-income")
    public ResponseEntity<Double> getTotalIncome(
            @PathVariable("id") int walletId,
            @RequestAttribute("userId") int userId) {
        
        try {
            double totalIncome = walletService.getTotalIncome(walletId, userId);
            return ResponseEntity.ok(totalIncome);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}/export-csv")
    public ResponseEntity<String> exportToCSV(
            @PathVariable("id") int walletId,
            @RequestAttribute("userId") int userId) {
        
        try {
            walletService.exportToCSV(walletId, userId);
            return ResponseEntity.ok("CSV export started successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
