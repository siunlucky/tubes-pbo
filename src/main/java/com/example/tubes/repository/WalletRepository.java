package com.example.tubes.repository;

import com.example.tubes.model.Wallet;
// import com.example.tubes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    List<Wallet> findByUserId(int userId);
    
    Wallet findByIdAndUserId(int id, int userId);
    
    boolean existsByNameAndUserId(String name, Long userId);
}