package com.yorkseed.backend.dto;

import com.yorkseed.backend.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Company email is required")
    @Email(message = "Please enter a valid company email address")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
    
    @NotNull(message = "Please select if you are a Founder or Investor")
    private UserRole role;
}