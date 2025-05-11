package com.example.tubes.model;

import java.util.Date;

public abstract class Transaction {
    private Long id;
    private double amount;
    private Date date;
    private String description;

    public Transaction(Long id, double amount, Date date, String desc) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = desc;
    }

    public Long getID() {
        return this.id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return this.amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract double getSummary();
}
