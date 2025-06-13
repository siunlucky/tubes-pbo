package com.example.tubes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubes.model.Category;
import com.example.tubes.model.Wallet;
import com.example.tubes.services.CategoryService;
import com.example.tubes.services.WalletService;
import com.example.tubes.utils.ApiResponse;

@RestController
@RequestMapping("/api/wallets/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final WalletService walletService;

    public CategoryController(
        CategoryService categoryService,
        WalletService walletService
    ) {
        this.categoryService = categoryService;
        this.walletService = walletService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategory(@RequestParam Long walletId) {
        Wallet wallet = walletService.getWalletById(walletId);

        List<Category> categories = categoryService.getAllCategory(wallet);
        return ResponseEntity.ok(ApiResponse.success(categories, "All categories retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id, @RequestParam Long walletId) {
        Wallet wallet = walletService.getWalletById(walletId);
        Category category = categoryService.getCategoryById(id, wallet);

        return ResponseEntity.ok(ApiResponse.success(category, "Category retrieved successfully"));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody Category category) {
        Wallet wallet = walletService.getWalletById(category.getWallet().getId());
        category.setWallet(wallet);

        Category saved = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(saved, "Category created successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategoryById(@PathVariable Long id, @RequestParam Long walletId) {
        Wallet wallet = walletService.getWalletById(walletId);
        categoryService.deleteCategory(id, wallet);

        return ResponseEntity.ok(ApiResponse.success(null, "Category deleted successfully"));
    }
}
