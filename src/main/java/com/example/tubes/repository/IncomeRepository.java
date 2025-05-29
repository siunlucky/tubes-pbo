package com.example.tubes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.tubes.model.Income;
import com.example.tubes.model.Wallet;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findAllByWallet(Wallet wallet);
}
