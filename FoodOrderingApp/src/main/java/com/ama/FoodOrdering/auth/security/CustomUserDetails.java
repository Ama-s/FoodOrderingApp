package com.ama.FoodOrdering.auth.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails extends User {
    private Long userId;

    public CustomUserDetails(UserDetails userDetails, Long userId) {
        super(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}

