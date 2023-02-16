package com.realjava.blogapp.controller;

import com.realjava.blogapp.payload.JwtAuthResponse;
import com.realjava.blogapp.payload.LoginDto;
import com.realjava.blogapp.payload.RegisterDto;
import com.realjava.blogapp.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        String responseToken = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(responseToken);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Register with role_user
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterDto registerDto) {
        String response = authService.signup(registerDto);
        return ResponseEntity.ok(response);
    }
}
