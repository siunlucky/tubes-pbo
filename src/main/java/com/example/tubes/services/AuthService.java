package com.example.tubes.services;

import java.util.Optional;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tubes.exception.BadRequestException;
import com.example.tubes.model.TokenBlacklist;
import com.example.tubes.model.User;
import com.example.tubes.repository.TokenBlacklistRepository;
import com.example.tubes.repository.UserRepository;
import com.example.tubes.utils.JwtUtil;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanupExpiredTokens() {
        tokenBlacklistRepository.deleteExpiredTokens(new Date());
    }

    public String register(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            throw new BadRequestException("Username already exists");
        }

        user.setPassword(this.hashPassword(user.getPassword()));

        userRepository.save(user);

        return JwtUtil.generateToken(user.getUsername());
    }

    public String login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new BadRequestException("Username/password is incorrect");
        }

        User user = userOptional.get();
        
        if (!this.matchPassword(password, user.getPassword())) {
            throw new BadRequestException("Username/password is incorrect");
        }

        return JwtUtil.generateToken(user.getUsername());
    }

    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        if (token == null || token.isEmpty()) {
            return;
        }
        
        try {
            Date expiryDate = JwtUtil.getExpirationDateFromToken(token);
            
            TokenBlacklist blacklistedToken = new TokenBlacklist(token, expiryDate);
            tokenBlacklistRepository.save(blacklistedToken);
        } catch (Exception e) {
            // return kosong
        }
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.findByToken(token).isPresent();
    }

    private String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private boolean matchPassword(String password, String hashedPassword) {
        return new BCryptPasswordEncoder().matches(password, hashedPassword);
    }

    public boolean reqResetPassword(String email) {
        // masih bingung iki
        return true;
    }
    
    public boolean resetPassword(String otp, String email, String newPassword) {
        // masih bingung iki
        return true;
    }
};