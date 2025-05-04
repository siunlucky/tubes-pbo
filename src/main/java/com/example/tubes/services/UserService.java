package com.example.tubes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tubes.model.User;
import com.example.tubes.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> getAll() {
        return repository.findAll();
    }

    public Optional<User> getById(Long id) {
        return repository.findById(id);
    }

    public User create(User user) {
        return repository.save(user);
    }

    public User update(Long id, User userData) {
        return repository.findById(id).map(user -> {
            user.setUsername(userData.getUsername());
            user.setPassword(userData.getPassword());
            user.setName(userData.getName());
            return repository.save(user);
        }).orElseThrow();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
