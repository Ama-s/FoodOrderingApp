package com.ama.FoodOrdering.controller;

import com.ama.FoodOrdering.auth.security.AuthResponse;
import com.ama.FoodOrdering.auth.security.JwtUtil;
import com.ama.FoodOrdering.auth.security.LoginRequest;
import com.ama.FoodOrdering.entities.User;
import com.ama.FoodOrdering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword()));

        User user = userService.findByName(loginRequest.getName());

        // to generate JWT token
        String token = jwtUtil.generateToken(loginRequest.getName(), user.getId(), user.getRole());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
