package com.example.tubes.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@DiscriminatorValue("INCOME")
public class Income extends Transaction {
    @Column(nullable = true)
    private String source;

    @Column(nullable = true)
    private String category;

    public Income() {
        super();
    }

    public Income(Long id, double amount, Date date, String description, String source, String category) {
        super(id, amount, date, description);
        this.source = source;
        this.category = category;
    }

    public String getSource() { 
        return source; 
    }

    public void setSource(String source) { 
        this.source = source; 
    }

    public String getCategory() { 
        return category; 
    }

    public void setCategory(String category) { 
        this.category = category; 
    }

    @Override
    public double getSummary() {
        return -1 * getAmount(); // contoh implementasi
    }
}
