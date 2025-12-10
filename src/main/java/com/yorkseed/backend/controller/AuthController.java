package com.yorkseed.backend.controller;

import com.yorkseed.backend.dto.ApiResponse;
import com.yorkseed.backend.dto.LoginRequest;
import com.yorkseed.backend.dto.LoginResponse;
import com.yorkseed.backend.dto.SignupRequest;
import com.yorkseed.backend.model.User;
import com.yorkseed.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        User user = userService.registerUser(signupRequest);
        return ResponseEntity.ok(ApiResponse.success(
                "Registration successful! Please check your email to verify your account.",
                null // Don't return user details until verified
        ));
    }
    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponse> verifyEmail(@RequestParam String token) {
        boolean verified = userService.verifyEmail(token);
        return ResponseEntity.ok(ApiResponse.success(
                verified ? "Email verified successfully!" : "Email already verified",
                null
        ));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }
}
