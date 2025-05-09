package com.example.tubes.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wallets")

public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    public Wallet() {}

    public Wallet(String name, User user) {
        this.name = name;
        this.user = user;
        this.balance = 0.0;
    }    

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public User getUser() { return user; }
    public List<Transaction> getTransactions() { return transactions; }

    public void setName(String name) { this.name = name; }
    public void setUser(User user) { this.user = user; }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setWallet(this);
        calculateBalance();
    }

    public void calculateBalance() {
        double total = 0;
        for (Transaction t : transactions) {
            total += t.getAmount(); 
        }
        this.balance = total;
    }

    // public double getTotalIncome() {
    //     return transactions.stream()
    //         .filter(t -> t instanceof Income)
    //         .mapToDouble(Transaction::getAmount)
    //         .sum();
    // }

    // public double getTotalExpense() {
    //     return transactions.stream()
    //         .filter(t -> t instanceof Expense)
    //         .mapToDouble(Transaction::getAmount)
    //         .sum();
    // }
}
