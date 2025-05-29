package com.example.tubes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tubes.model.Category;
import com.example.tubes.model.Transaction;
import com.example.tubes.model.Wallet;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findAllByWallet(Wallet wallet);
    public Optional<Category> findByIdAndWallet(Long id, Wallet wallet);
}

