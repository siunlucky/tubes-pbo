package com.example.tubes.services;

import com.example.tubes.exception.NotFoundException;
import com.example.tubes.exception.ResourceNotFoundException;
import com.example.tubes.model.Category;
import com.example.tubes.model.Transaction;
import com.example.tubes.model.User;
import com.example.tubes.model.Wallet;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.tubes.repository.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(
        CategoryRepository categoryRepository
    ) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategory(Wallet wallet){
        return categoryRepository.findAllByWallet(wallet);
    }

    public Category getCategoryById(Long id, Wallet wallet) {
        Optional<Category> categoryOptional = categoryRepository.findByIdAndWallet(id, wallet);
        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("Category not found with id: " + id);
        }

        Category category = categoryOptional.get();
        return category;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id, Wallet wallet) {
        Optional<Category> categoryOptional = categoryRepository.findByIdAndWallet(id, wallet);
        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("Category not found with id: " + id);
        }
        Category category = categoryOptional.get();
        
        categoryRepository.delete(category);
    }
}
