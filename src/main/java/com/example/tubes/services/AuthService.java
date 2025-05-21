package com.example.tubes.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tubes.exception.BadRequestException;
import com.example.tubes.model.User;
import com.example.tubes.repository.UserRepository;
import com.example.tubes.utils.JwtUtil;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

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



    private String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private boolean matchPassword(String password, String hashedPassword) {
        return new BCryptPasswordEncoder().matches(password, hashedPassword);
    }
}
