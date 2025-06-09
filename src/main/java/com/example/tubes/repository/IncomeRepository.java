package com.example.tubes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.tubes.model.Income;
import com.example.tubes.model.Wallet;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findAllByWallet(Wallet wallet);

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Transaction i " +
       "WHERE i.wallet = :wallet " +
       "AND i.transactionType = 'INCOME' " +
       "AND EXTRACT(MONTH FROM i.date) = EXTRACT(MONTH FROM CURRENT_DATE) " +
       "AND EXTRACT(YEAR FROM i.date) = EXTRACT(YEAR FROM CURRENT_DATE)")
    Double sumByCurrentMonthByWallet(Wallet wallet);

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Transaction i " +
       "WHERE i.wallet = :wallet " +
       "AND i.transactionType = 'INCOME' " +
       "AND EXTRACT(YEAR FROM i.date) = EXTRACT(YEAR FROM CURRENT_DATE)")
    Double sumByCurrentYearByWallet(Wallet wallet);
}
