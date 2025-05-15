package com.example.tubes.model;

import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "expenses")
public class Expense extends Transaction {
    @Column(nullable = false)
    private String destination;
    
    @Column(nullable = false)
    private String category;
    
    public Expense() {
        super();
    }

    public Expense (Long id, double amount, Date date, String desc, String destination, String category) {
        super(id, amount, date, desc);
        setID(id);
        this.destination = destination;
        this.category = category;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getSummary() {
        return super.getAmount();
    }
    
}
