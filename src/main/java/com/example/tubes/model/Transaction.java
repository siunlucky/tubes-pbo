package com.example.tubes.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    

    @Column(nullable = false)
    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "transaction_type", insertable = false, updatable = false)
    private String transactionType;

    public Transaction() {}

    public Transaction(Long id, double amount, Date date, String description) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }


    public abstract double getSummary();

    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public double getAmount() { 
        return amount; 
    }

    public void setAmount(double amount) { 
        this.amount = amount; 
    }

    public Date getDate() { 
        return date; 
    }

    public void setDate(Date date) { 
        this.date = date; 
    }

    public String getDescription() { 
        return description; 
    }

    public void setDescription(String description) { 
        this.description = description; 
    }

    public Wallet getWallet() { 
        return wallet; 
    }

    public void setWallet(Wallet wallet) { 
        this.wallet = wallet; 
    }
        
    public String getTransactionType() {
        return transactionType;
    }
}
