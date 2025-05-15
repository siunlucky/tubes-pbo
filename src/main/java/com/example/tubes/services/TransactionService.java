package com.example.tubes.services;

import com.example.tubes.model.Transaction;
import com.example.tubes.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found by id" + id));
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction updateTransaction) {
        Transaction updt = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found by id " + id));

        updt.setAmount(updateTransaction.getAmount());
        updt.setDate(updateTransaction.getDate());
        updt.setDescription(updateTransaction.getDescription());
        updt.setWallet(updateTransaction.getWallet());

        return transactionRepository.save(updt);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

}
