package com.example.tubes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.tubes.model.Transaction;
import java.util.List;

public interface  TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByID(Long iD);
}
