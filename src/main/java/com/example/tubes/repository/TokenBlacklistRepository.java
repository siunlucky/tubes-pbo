package com.example.tubes.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tubes.model.TokenBlacklist;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    Optional<TokenBlacklist> findByToken(String token);
    
    @Query("DELETE FROM TokenBlacklist t WHERE t.expiryDate < :now")
    void deleteExpiredTokens(@Param("now") Date now);
}