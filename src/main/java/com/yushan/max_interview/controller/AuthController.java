package com.yushan.max_interview.controller;

import com.yushan.max_interview.entity.ApiResponse;
import com.yushan.max_interview.entity.User;
import com.yushan.max_interview.entity.UserLoginRequest;
import com.yushan.max_interview.entity.UserRegisterRequest;
import com.yushan.max_interview.repository.UserRepository;
import com.yushan.max_interview.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<String >register(@Valid @RequestBody UserRegisterRequest request) {
        User user = User.builder()
            .userName(request.getPhoneNumber())
            .password(request.getPassword())
            .email(request.getEmail())
            .coverImage(request.getCoverImage())
            .biography(request.getBiography())
            .createdAt(LocalDateTime.now())
            .build();
        return authService.register(user);
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody UserLoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/test")
    public ResponseEntity<List<User>> test() {
        List<User> all = userRepository.findAll();
        return ResponseEntity.ok(all);
    }
}
