package com.example.tubes.model;

import java.util.Date;

public class Income extends Transaction{
    private String source;
    private String category;

    public Income(Long id, double amount, Date date, String desc, String source, String category) {
        super(id, amount, date, desc);
        this.source = source;
        this.category = category;
    }

    public double getSummary() {
        return -1*super.getAmount();
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
