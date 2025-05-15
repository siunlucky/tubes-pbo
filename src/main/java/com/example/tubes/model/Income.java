package com.example.tubes.model;

import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "incomes")
public class Income extends Transaction{
    @Column(nullable = false)
    private String source;
    
    @Column(nullable = false)
    private String category;
    
    public Income() {
        super();
    }

    public Income(Long id, double amount, Date date, String description, String source, String category) {
        super(id, amount, date, description);
        setID(id);
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
