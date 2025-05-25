package com.example.tubes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tubes.exception.BadRequestException;
import com.example.tubes.exception.ResourceNotFoundException;
import com.example.tubes.model.User;
import com.example.tubes.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() -> 
            new ResourceNotFoundException("User not found with id: " + id));
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> 
            new ResourceNotFoundException("User not found with username: " + username));
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User create(User user) {
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User update(Long id, User userData) {
        return repository.findById(id).map(user -> {
            user.setUsername(userData.getUsername());
            
            if (userData.getPassword() != null && !userData.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userData.getPassword()));
            }
            
            user.setName(userData.getName());
            // user.setEmail(userData.getEmail());
            return repository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
