package com.example.tubes.model;

import java.util.Date;

public class Expense extends Transaction {
    private String destination;
    private String category;

    public Expense (Long id, double amount, Date date, String desc, String destination, String category) {
        super(id, amount, date, desc);
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
