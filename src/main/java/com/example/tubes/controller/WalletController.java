package com.example.tubes.controller;

import com.example.tubes.model.Wallet;
import com.example.tubes.services.WalletService;
import com.example.tubes.utils.ApiResponse;
import com.example.tubes.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/wallets")
public class WalletController {
    @Autowired
    private WalletService walletService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Wallet>>> getAllWallets() {
        try {
            List<Wallet> wallets = walletService.getAllWallets();
            return ResponseEntity.ok(ApiResponse.success(wallets, "All wallets retrieved successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }   
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Wallet>> getWalletById(@PathVariable Long id) {
        try {
            Wallet wallet = walletService.getWalletById(id);
            return ResponseEntity.ok(ApiResponse.success(wallet, "Wallet retrieved successfully"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Wallet>> createWallet(@RequestBody Wallet wallet) {
        try {
            Wallet createdWallet = walletService.createWallet(wallet);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createdWallet, "Wallet created successfully", 201));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Wallet>> updateWallet(@PathVariable Long id, @RequestBody Wallet wallet) {
        try {
            if (wallet.getId() != null && !wallet.getId().equals(id)) {
                throw new RuntimeException("Wallet ID in the request body does not match the path variable");
            }
            Wallet updatedWallet = walletService.updateWallet(id, wallet);
            return ResponseEntity.ok(ApiResponse.success(updatedWallet, "Wallet updated successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteWallet(@PathVariable Long id) {
        try {
            walletService.deleteWallet(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Wallet deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<ApiResponse<Double>> getWalletBalance(@PathVariable Long id) {
        try {
            double balance = walletService.calculateWalletBalance(id);
            return ResponseEntity.ok(ApiResponse.success(balance, "Wallet balance retrieved successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping("/{id}/expense")
    public ResponseEntity<ApiResponse<Double>> getTotalExpense(@PathVariable Long id) {
        try {
            double expense = walletService.getTotalExpense(id);
            return ResponseEntity.ok(ApiResponse.success(expense, "Total expense retrieved successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping("/{id}/income")
    public ResponseEntity<ApiResponse<Double>> getTotalIncome(@PathVariable Long id) {
        try {
            double income = walletService.getTotalIncome(id);
            return ResponseEntity.ok(ApiResponse.success(income, "Total income retrieved successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }
}