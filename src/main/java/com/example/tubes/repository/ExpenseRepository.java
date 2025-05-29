package com.example.tubes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.tubes.model.Expense;
import com.example.tubes.model.Wallet;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByWallet(Wallet wallet);
}
