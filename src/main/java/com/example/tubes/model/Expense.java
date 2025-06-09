package com.example.tubes.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("EXPENSE")
public class Expense extends Transaction {
    @Column(nullable = true)
    private String destination;

    @Column(nullable = true)
    private String category;

    public Expense() {
        super();
    }

    public Expense(Long id, double amount, Date date, String description, String destination, String category) {
        super(id, amount, date, description);
        this.destination = destination;
        this.category = category;
    }

    public String getDestination() { 
        return destination; 
    }

    public void setDestination(String destination) { 
        this.destination = destination; 
    }

    public String getCategory() { 
        return category; 
    }

    public void setCategory(String category) { 
        this.category = category; 
    }

    @Override
    public double getValue() {
        return -1 * getAmount();
    }
}
