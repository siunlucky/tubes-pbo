package com.example.tubes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tubes.model.User;
import com.example.tubes.services.UserService;
import com.example.tubes.utils.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> getCurrentUser() {
        User user = this.userService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(user, "Current user fetched successfully"));
    }
}