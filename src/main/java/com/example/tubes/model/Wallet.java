package com.example.tubes.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "wallets")
public class Wallet implements Exportable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private double balance;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();
    
    public Wallet() {
    }
    
    public Wallet(String name, double balance) {
        this.name = name;
        this.balance = balance; 
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setWallet(this);
        calculateBalance();
    }
    
    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        transaction.setWallet(null);
        calculateBalance();
    }
    
    public void calculateBalance() {
        double totalIncome = getTotalIncome();
        double totalExpense = getTotalExpense();
        this.balance = totalIncome - totalExpense;
    }
    
    public double getTotalExpense() {
        return transactions.stream()
                .filter(t -> t instanceof Expense)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t instanceof Income)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    public Map<String, Object> showStatistics() {
        double totalIncome = getTotalIncome();
        double totalExpense = getTotalExpense();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("walletName", name);
        stats.put("currentBalance", balance);
        stats.put("totalIncome", totalIncome);
        stats.put("totalExpense", totalExpense);
        stats.put("transactionCount", transactions.size());
        
        return stats;
    }

    // @Override
    public String exportToCSV() {
        StringBuilder csv = new StringBuilder();
        csv.append("id,date,description,amount,type,category,source/destination\n");
        
        for (Transaction transaction : transactions) {
            csv.append(transaction.getId()).append(",");
            csv.append(transaction.getDate()).append(",");
            csv.append("\"").append(transaction.getDescription()).append("\",");
            csv.append(transaction.getAmount()).append(",");
            
            if (transaction instanceof Income) {
                Income income = (Income) transaction;
                csv.append("income,");
                csv.append(income.getCategory()).append(",");
                csv.append(income.getSource());
            } else if (transaction instanceof Expense) {
                Expense expense = (Expense) transaction;
                csv.append("expense,");
                csv.append(expense.getCategory()).append(",");
                csv.append(expense.getDestination());
            }
            
            csv.append("\n");
        }
        
        return csv.toString();
    }
}
