package com.yorkseed.backend.dto;

import com.yorkseed.backend.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private boolean success;
    private String message;
    private String token; // We'll implement JWT later
    private User user;
    
    public static LoginResponse success(String message, User user) {
        return LoginResponse.builder()
                .success(true)
                .message(message)
                .user(user)
                .build();
    }
    
    public static LoginResponse error(String message) {
        return LoginResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}
