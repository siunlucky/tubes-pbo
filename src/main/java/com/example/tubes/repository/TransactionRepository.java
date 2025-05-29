package com.example.tubes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.tubes.model.Transaction;
import com.example.tubes.model.Wallet;

public interface  TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findAllByWallet(Wallet wallet);
    public Optional<Transaction> findByIdAndWallet(Long id, Wallet wallet);
}
