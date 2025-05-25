package com.example.tubes.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "users")

public class User {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    // @Column(nullable = false)
    // private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Wallet> wallets = new ArrayList<>();

    public User() {
    }
    
    public User(String username, String password, String name ) {
        this.username = username;
        this.password = password;
        this.name = name;
        // this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public String getEmail() {
    //     return email;
    // }
    
    // public void setEmail(String email) {
    //     this.email = email;
    // }

    public List<Wallet> getWallets() {
        return wallets;
    }
    
    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }
    
    public void addWallet(Wallet wallet) {
        wallets.add(wallet);
        wallet.setUser(this);
    }
    
    public void removeWallet(Wallet wallet) {
        wallets.remove(wallet);
        wallet.setUser(null);
    }
    
    public double getTotalExpense() {
        return wallets.stream().mapToDouble(Wallet::getTotalExpense).sum();
    }
    
    public double getTotalIncome() {
        return wallets.stream().mapToDouble(Wallet::getTotalIncome).sum();
    }
}
