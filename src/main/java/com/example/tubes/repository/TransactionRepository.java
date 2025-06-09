package com.example.tubes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tubes.model.Transaction;
import com.example.tubes.model.Wallet;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findAllByWallet(Wallet wallet);
    public Optional<Transaction> findByIdAndWallet(Long id, Wallet wallet);
    @Query(value = """
        SELECT t.category, COUNT(t.id) 
        FROM transactions t 
        WHERE t.wallet_id = :#{#wallet.id} 
        AND EXTRACT(MONTH FROM t.date) = EXTRACT(MONTH FROM CURRENT_DATE)
        AND EXTRACT(YEAR FROM t.date) = EXTRACT(YEAR FROM CURRENT_DATE)
        GROUP BY t.category
    """, nativeQuery = true)
    List<Object[]> sumCategoryCurrentMonthByWallet(Wallet wallet);

    @Query(value = """
        SELECT t.category, COUNT(t.id) 
        FROM transactions t 
        WHERE t.wallet_id = :#{#wallet.id} 
        AND EXTRACT(YEAR FROM t.date) = EXTRACT(YEAR FROM CURRENT_DATE)
        GROUP BY t.category
    """, nativeQuery = true)
    List<Object[]> sumCategoryCurrentYearByWallet(Wallet wallet);
}
