package com.example.tubes.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "wallets")
public class Wallet implements Exportable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private double balance;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();
    
    public Wallet() {
    }
    
    public Wallet(String name, User user) {
        this.name = name;
        this.user = user;
        this.balance = 0.0;
    }
    
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setWallet(this);
        calculateBalance();
    }
    
    public void showStatistics() {
        double totalIncome = getTotalIncome();
        double totalExpense = getTotalExpense();
        
        System.out.println("Wallet: " + name);
        System.out.println("Current Balance: " + balance);
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Number of Transactions: " + transactions.size());
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
    
    public User getUser() {
        return this.user;
    }
    
    @Override
    public void exportToCSV() {
        System.out.println("Exporting wallet " + name + " data to CSV...");
        // Logic to export transactions to CSV file need to be implemented here
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
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
    
    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}
